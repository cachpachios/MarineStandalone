package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTLong implements NBTTag{

	long data;
	private final String name;
	
	public NBTLong(String name, long v) {
		data = v;
		this.name = name;
	}
	
	public NBTLong(String name, ByteData data) {
		this.data = data.readShort();
		this.name = name;
	}
	
	@Override
	public byte getTagID() {
		return 4;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeLong(data);
		return d.getBytes();
	}
	
	public long toLong() {
		return data;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData d = new ByteData();
		d.writeLong(data);
		return d.getBytes();
	}
}
