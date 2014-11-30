package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTInteger implements NBTTag{
	private int data;
		
	private final String name;
	
	public NBTInteger(String name, int v) {
		this.name = name;
		data = v;
	}
		
	public NBTInteger(String name, ByteData data) {
		this.name = name;
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
		d.writeUTF8Short(name);
		d.writeInt(data);
		return d.getBytes();
	}
	
	public int toInt() {
		return data;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData data = new ByteData();
		data.writeInt((this.data));
		return data.getBytes();
	}
}
