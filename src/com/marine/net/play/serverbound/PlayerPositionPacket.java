package com.marine.net.play.serverbound;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;

public class PlayerPositionPacket extends Packet {

	public double X,Y,Z; // Absolute Position (Y is feets, head = Y+1.62)
	public boolean onGround; // Inverted isAirborn
	
	@Override
	public int getID() {
		return 0x04;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {} // Serverbound packet only!

	@Override
	public void readFromBytes(ByteData input) {
		X = input.readDouble();
		Y = input.readDouble();
		Z = input.readDouble();
		
		onGround = input.readBoolean();
	}

	@Override
	public States getPacketState() {
		// TODO Auto-generated method stub
		return null;
	}

}
