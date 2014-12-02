package com.marine.net.play.clientbound.windows;

import java.io.IOException;
import java.io.OutputStream;

import com.marine.game.windows.SlotData;
import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.States;

public class WindowItemsPacket extends Packet{
	final byte windowID;
	final SlotData[] data;
	
	public WindowItemsPacket(byte windowID, SlotData[] data) {
		this.windowID = windowID;
		this.data = data;
	}

	@Override
	public int getID() {
		return 0x30;
	}

	@Override
	public void writeToStream(OutputStream stream) throws IOException {
		ByteData d = new ByteData();
		d.writeVarInt(getID());
		
		d.writeByte(windowID);
		d.writeShort((short) data.length);
		for(SlotData slot : data)
		d.writeByte(slot.toBytes());
		
	}

	@Override
	public void readFromBytes(ByteData input) {}

	@Override
	public States getPacketState() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
