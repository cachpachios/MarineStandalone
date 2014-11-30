package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTShort implements NBTTag{

	private short data;
	private String name;
	
	public NBTShort(String name, short v) {
		data = v;
		this.name = name;
	}
	
	
	public NBTShort(String name, ByteData data) {
		this.data = data.readShort();
		this.name = name;
	}
	
	@Override
	public byte getTagID() {
		return 2;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeShort(data);
		return d.getBytes();
	}
	
	public short toShort() {
		return data;
	}


	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData data = new ByteData();
		data.writeShort(this.data);
		return data.getBytes();
	}
}
