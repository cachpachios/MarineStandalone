package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTIntArray implements NBTTag {

	int[] array;

	private int size;
	
	private final String name;
	
	public NBTIntArray(String name, ByteData data) {
		this.name = name;
		size = data.readInt();
		array = new int[size];
		for(int i = 0; i <= size;i++) {
			array[i] = data.readInt();
		}
	}
	
	public NBTIntArray(String name, int[] v) {
		this.name = name;
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
		d.writeUTF8Short(name);
		d.writeInt(array.length);
		for(int x : array)
			d.writeInt(x);
		return d.getBytes();
	}

	@Override
	public String getName() {
		return name;
	}
	

	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData d = new ByteData();
		d.writeInt(array.length);
		for(int x : array)
			d.writeInt(x);
		return d.getBytes();
	}
}
