package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTInteger implements NBTTag{
	private int data;
		
	public NBTInteger(int v) {
		data = v;
	}
		
	public NBTInteger(ByteData data) {
		this.data = data.readInt();
	}
	
	@Override
	public byte getTagID() {
		return 3;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeInt(data);
		return d.getBytes();
	}
	
	public int toInt() {
		return data;
	}
}
