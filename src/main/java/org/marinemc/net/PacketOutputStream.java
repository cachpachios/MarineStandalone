///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.net;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.SocketException;

import org.marinemc.io.ByteCompressor;
import org.marinemc.io.ByteCompressor.EncodingUseless;
import org.marinemc.io.binary.ByteUtils;
import org.marinemc.io.binary.CompressableStoredByteOutput;
import org.marinemc.util.ObjectMeta;

/**
 * @author Fozie
 */
public class PacketOutputStream { // Here we enable encryption and compression
									// if needed
	WeakReference<Client> c;

	public PacketOutputStream(final Client c) {
		this.c = new WeakReference<>(c);
	}

	boolean referenceCheck() {
		return c.get() == null;
	}

	private void finalWrite(final byte[] uncompressed) throws IOException,
			SocketException {
		if (referenceCheck())
			return;

		if (c.get().getConnection().isClosed())
			return;

		if (!c.get().isActive())
			return;

		if (c.get().getCompressionThreshold() == -1)
			c.get()
					.getConnection()
					.getOutputStream()
					.write(ByteUtils.putFirst(
							ByteUtils.VarInt(uncompressed.length), uncompressed));
		else if (uncompressed.length < c.get().getCompressionThreshold())
			c.get().getConnection().getOutputStream()
					.write(ByteUtils.putFirst(Client.NULL_BYTE, uncompressed));
		else {
			byte[] output;
			if (uncompressed.length < c.get().getCompressionThreshold())
				output = ByteUtils.putFirst(Client.NULL_BYTE, uncompressed);
			try {
				output = ByteUtils.putFirst(ByteUtils
						.VarInt(uncompressed.length), ByteCompressor.instance()
						.encode(uncompressed));
			} catch (final EncodingUseless e) { // This means compression is
												// waste of brandwith and it
												// will send uncompressed data
				output = ByteUtils.putFirst(Client.NULL_BYTE, uncompressed);
			}
			try {
			c.get()
					.getConnection()
					.getOutputStream()
					.write(ByteUtils.putFirst(ByteUtils.VarInt(output.length),
							output));
			} catch(SocketException e) {}
		}
	}

	private void finalWrite(final CompressableStoredByteOutput uncompressed)
			throws IOException, SocketException {
		if (referenceCheck())
			return;

		if (c.get().getConnection().isClosed())
			return;

		if (!c.get().isActive())
			return;

		if (c.get().getCompressionThreshold() == -1) {
			uncompressed.writeVarInt(0, uncompressed.size());
			c.get().getConnection().getOutputStream()
					.write(uncompressed.toBytes());
		} else {
			if (uncompressed.size() < c.get().getCompressionThreshold())
				uncompressed.writeByte(0, (byte) 0);
			else
				try {
					final int uncompressedSize = uncompressed.size();
					uncompressed.compress();
					uncompressed.writeVarInt(0, uncompressedSize);
				} catch (final EncodingUseless e) {
					uncompressed.writeByte(0, (byte) 0);
				}

			uncompressed.writeVarInt(0, uncompressed.size());

			try {
			c.get().getConnection().getOutputStream()
					.write(uncompressed.toBytes());
			} catch(SocketException e) {}
		}
	}

	public void write(final int id, final byte[] b) throws IOException {
		finalWrite(ByteUtils.putFirst(ByteUtils.VarInt(id), b));
	}

	public void writeNoCompress(final int id, byte[] b) throws IOException {
		if (referenceCheck())
			return;

		if (c.get().getCompressionThreshold() == -1)
			c.get().getConnection().getOutputStream()
					.write(ByteUtils.putFirst(ByteUtils.VarInt(b.length), b));
		else {
			b = ByteUtils.putFirst(Client.NULL_BYTE, b);
			b = ByteUtils.putFirst(ByteUtils.VarInt(b.length), b);
			c.get().getConnection().getOutputStream().write(b);
		}
	}

	public void write(final int id, final CompressableStoredByteOutput data)
			throws IOException {
		data.writeVarInt(0, id);
		finalWrite(data);
	}

	public static Integer readVarIntFromStream(final InputStream stream) {
		int out = 0;
		int bytes = 0;
		byte in;
		int x;
		while (true) {
			try {
				x = stream.read();
			} catch (final IOException e) {
				return null;
			}

			if (x == -1)
				return null;

			in = (byte) x;
			out |= (in & 0x7F) << bytes++ * 7;
			if ((in & 0x80) != 0x80)
				break;
		}
		return out;
	}

	public static ObjectMeta<Integer, Integer> readVarIntWithLength(
			final InputStream stream) {
		int out = 0;
		int bytes = 0;
		byte in;
		int x;
		while (true) {
			try {
				x = stream.read();
			} catch (final IOException e) {
				return null;
			}

			if (x == -1)
				return null;

			in = (byte) x;
			out |= (in & 0x7F) << bytes++ * 7;
			if ((in & 0x80) != 0x80)
				break;
		}
		return new ObjectMeta<>(out, bytes);
	}
}
