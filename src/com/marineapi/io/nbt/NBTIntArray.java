package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTIntArray implements NBTTag {

	int[] array;

	private int size;
	
	public NBTIntArray(ByteData data) {
		size = data.readInt();
		array = new int[size];
		for(int i = 0; i <= size;i++) {
			array[i] = data.readInt();
		}
	}
	
	public NBTIntArray(int[] v) {
		array = v;
	}
	
	@Override
	public byte getTagID() {
		return 7;
	}

	public int[] getIntArray() {
		return array;
	}
	
	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeInt(array.length);
		for(int x : array)
			d.writeInt(x);
		return d.getBytes();
	}
}
