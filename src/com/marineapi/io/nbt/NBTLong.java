package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTLong implements NBTTag{

	long data;
	
	public NBTLong(long v) {
		data = v;
	}
	
	public NBTLong(ByteData data) {
		this.data = data.readShort();
	}
	
	@Override
	public byte getTagID() {
		return 4;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeLong(data);
		return d.getBytes();
	}
	
	public long toLong() {
		return data;
	}
}
