package org.marinemc.net.packets.login;

import java.io.IOException;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

public class CompressionPacket extends Packet {

	final int threshould;

	public CompressionPacket(final int threshould) {
		super(0x03, States.GLOBAL);
		this.threshould = threshould;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList l = new ByteList();
		l.writeVarInt(threshould);
		stream.write(getID(), l);
	}
}
