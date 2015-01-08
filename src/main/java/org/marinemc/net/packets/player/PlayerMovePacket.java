package org.marinemc.net.packets.player;

import org.marinemc.net.Packet;
import org.marinemc.net.States;

public class PlayerMovePacket extends Packet {

	public PlayerMovePacket() {
		super(0x04, States.INGAME);
	}

}
