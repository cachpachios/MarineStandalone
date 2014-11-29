package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTByteArray implements NBTTag {
	private ByteData array;

	public NBTByteArray(ByteData data) {
		int l = data.readInt();
		array = data.readData(l);
	}
	
	public NBTByteArray(byte[] v) {
		array = new ByteData(v);
	}
	
	@Override
	public byte getTagID() {
		return 7;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeInt(array.getLength());
		d.writeend(array.getBytes());
		return d.getBytes();
	}
}
