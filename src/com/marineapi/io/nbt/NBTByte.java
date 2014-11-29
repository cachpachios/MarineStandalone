package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTByte implements NBTTag {

	private byte data;
	
	public NBTByte(byte b) {
		data = b;
	}
	
	public NBTByte(ByteData data) {
		this.data = data.readByte();
	}
	
	@Override
	public byte getTagID() {
		return 1;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeByte(data);
		return d.getBytes();
	}
	
	public byte toByte() {
		return data;
	}
	
}
