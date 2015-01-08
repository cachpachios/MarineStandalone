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
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.zip.DataFormatException;

import org.marinemc.events.standardevents.ClientTerminationEvent;
import org.marinemc.game.player.Player;
import org.marinemc.io.ByteCompressor;
import org.marinemc.logging.Logging;
import org.marinemc.net.packets.login.CompressionPacket;
import org.marinemc.server.Marine;
import org.marinemc.util.ObjectMeta;

/**
 * @author Fozie
 */
public class Client {
	final static byte[] NULL_BYTE = new byte[] { 0 };
	private final Socket connection;
	private final PacketOutputStream output;
	private final PacketQue que;
	private final InputStream input;
	private States state;
	private int compressionThreshold = -1;
	// For indexing in IngameInterceptor
	// private String userName;
	private short uid; // Saved as Short not short for the ability to equal null
	private boolean sending = false, waiting = false;
	private boolean isActive;

	public Client(final Socket s) throws IOException {
		state = States.HANDSHAKE;
		connection = s;
		input = s.getInputStream();
		output = new PacketOutputStream(this);
		uid = -1;
		isActive = true;
		que = new PacketQue(this);
	}

	public void sendPacket(final Packet packet) {
		if (sending) {
			que.add(packet);
			if (!waiting) {
				waiting = true;
				for (;;) {
					if (!sending)
						que.executePackets();
					waiting = false;
				}
			}
			return;
		}
		sending = true;
		// TODO: PacketBuffer
		try {
			packet.writeToStream(output);
			connection.getOutputStream().flush();
		} catch (final IOException e) {
			isActive = false;
		}
		sending = false;
	}

	public synchronized void sendPackets(final Collection<Packet> packets) { // TODO:
																				// PacketBuffer
		if (getConnection().isClosed())
			return;
		for (final Packet packet : packets)
			try {
				packet.writeToStream(output);
				connection.getOutputStream().flush();
			} catch (final IOException e) {
				e.printStackTrace();
			}
	}

	public NetworkManager getNetwork() {
		return Marine.getServer().getNetworkManager();
	}

	public InetAddress getAdress() {
		return connection.getInetAddress();
	}

	protected Socket getConnection() {
		return connection;
	}

	public void setState(final int state) {
		if (state == 0)
			this.state = States.HANDSHAKE;
		else if (state == 1)
			this.state = States.INTRODUCE;
		else if (state == 2)
			this.state = States.LOGIN;
		else if (state == 3)
			this.state = States.INGAME;
	}

	public States getState() {
		return state;
	}

	public void setState(final States state) {
		this.state = state;
	}

	public void terminate() {
		Marine.getServer().callEvent(
				new ClientTerminationEvent(connection.getInetAddress()));
		try {
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			connection.getInputStream().close();
			connection.close();
		} catch (final IOException e) {
		}
	}

	public boolean isActive() {
		return isActive;
	}

	/**
	 * Checks the connection and returns true if its closed
	 * 
	 * @return
	 */
	boolean tryConnection() {
		if (isActive)
			try { // Write a 0 bit to check if available
				getConnection().getOutputStream().write(NULL_BYTE);
			} catch (final IOException e1) {
				isActive = false;
				return false;
			}

		return true;
	}

	public Player getPlayer() {
		return Marine.getPlayer(uid);
	}

	public ConnectionStatus process() { // Returns true if connection is closed.
		while (true) {
			Integer l = 0;

			l = PacketOutputStream.readVarIntFromStream(input);

			if (l == null)
				return ConnectionStatus.CLOSED;

			if (l == 0)
				return ConnectionStatus.EMPTY;

			if (compressionThreshold == -1) {
				final byte[] packet = new byte[l];

				try {
					input.read(packet);
				} catch (final IOException e) {
					return ConnectionStatus.CLOSED;
				}
				getNetwork().packetHandler.rawIntercept(this, packet);
			} else {
				final ObjectMeta<Integer, Integer> preCompress = PacketOutputStream
						.readVarIntWithLength(input);
				if (preCompress.get() == 0) {
					final byte[] packet = new byte[l - 1];

					try {
						input.read(packet);
					} catch (final IOException e) {
						return ConnectionStatus.CLOSED;
					}
					getNetwork().packetHandler.rawIntercept(this, packet);
				} else {
					final byte[] compressed = new byte[l
							- preCompress.getMeta()];

					try {
						input.read(compressed);
					} catch (final IOException e) {
						return ConnectionStatus.CLOSED;
					}
					try {
						getNetwork().packetHandler.rawIntercept(this,
								ByteCompressor.instance().decode(compressed));
					} catch (final DataFormatException e) {
						Logging.getLogger().warn(
								"An packet was unable to be decompressed from client: "
										+ getAdress().getHostAddress(), e);
						continue;
					}
				}
			}

		}

	}

	public int getCompressionThreshold() {
		return compressionThreshold;
	}

	public void setCompressionThreshold(final int compressionThreshold) {
		if (!(state != States.HANDSHAKE && state != States.INTRODUCE))
			return;
		sendPacket(new CompressionPacket(compressionThreshold));
		this.compressionThreshold = compressionThreshold;
	}

	public boolean compressionEnabled() {
		return compressionThreshold != -1;
	}

	public short getUID() {
		return uid;
	}

	public void setUID(final short uid) {
		this.uid = uid;
	}

	public enum ConnectionStatus {
		EMPTY, PROCESSED, CLOSED
	}

}
