package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTShort implements NBTTag{

	private short data;
	
	public NBTShort(short v) {
		data = v;
	}
	
	public NBTShort(ByteData data) {
		this.data = data.readShort();
	}
	
	@Override
	public byte getTagID() {
		return 2;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeShort(data);
		return d.getBytes();
	}
	
	public short toShort() {
		return data;
	}
}
