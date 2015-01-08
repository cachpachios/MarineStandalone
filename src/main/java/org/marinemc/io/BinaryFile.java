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

package org.marinemc.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.marinemc.io.binary.ByteArray;
import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.Byteable;

/**
 * @author Fozie
 */
public class BinaryFile {

	File file;
	ByteArray data;

	public BinaryFile(final File f) {
		file = f;
		data = null;
	}

	public BinaryFile(final File f, final Byteable v) {
		if (v instanceof ByteArray)
			data = (ByteArray) v;
		else
			data = new ByteArray(v.toBytes());
		file = f;
	}

	public static InputStream decompressStream(final InputStream input)
			throws IOException {
		final PushbackInputStream pb = new PushbackInputStream(input, 2); // We
																			// need
																			// a
																			// pushbackstream
																			// to
																			// look
																			// ahead
																			// for
																			// the
																			// signature
		final byte[] signature = new byte[2];
		pb.read(signature); // Read the signature
		pb.unread(signature); // Push back the signature to the stream
		if (signature[0] == (byte) 0x1f && signature[1] == (byte) 0x8b) // Check
																		// if
																		// the
																		// signature
																		// matches
																		// standard
																		// gzip
																		// magic
																		// number
			return new GZIPInputStream(pb);
		else
			return pb;
	}

	public BinaryFile readBinary() throws IOException {
		if (!file.canRead())
			throw new IOException("Can't read file: " + file.getName());
		if (!file.exists())
			throw new FileNotFoundException("File not found: " + file.getName());
		final byte[] r = new byte[(int) file.length()];
		final InputStream input = new BufferedInputStream(new FileInputStream(
				file));
		input.read(r);
		data = new ByteArray(r);
		input.close();
		return this;
	}

	public BinaryFile readGZIPBinary() throws IOException {
		if (!file.canRead())
			throw new IOException("Can't read file: " + file.getName());
		if (!file.exists())
			throw new FileNotFoundException("File not found: " + file.getName());
		final byte[] r = new byte[(int) file.length()];
		final InputStream input = decompressStream(new BufferedInputStream(
				new FileInputStream(file)));
		input.read(r);
		data = new ByteArray(r);
		input.close();
		return this;
	}

	public void writeBinary() throws IOException {
		if (data == null)
			return;
		if (!file.exists())
			file.createNewFile();
		final OutputStream output = new BufferedOutputStream(
				new FileOutputStream(file));
		output.write(data.toBytes());
		output.close();
	}

	public void writeGZIPBinary() throws IOException {
		if (data == null)
			return;
		if (!file.exists())
			file.createNewFile();
		final GZIPOutputStream output = new GZIPOutputStream(
				new BufferedOutputStream(new FileOutputStream(file)));
		output.write(data.toBytes());
		output.close();
	}

	public ByteInput getData() {
		return data;
	}

	public byte[] getBytes() {
		return data.toBytes();
	}
}
