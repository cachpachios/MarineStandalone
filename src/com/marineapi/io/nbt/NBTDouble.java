package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTDouble  implements NBTTag{
	private double data;
		
	private final String name;
	
	public NBTDouble(String name, double v) {
		this.name = name;
		data = v;
	}
		
	public NBTDouble(String name, ByteData data) {
		this.name = name;
		this.data = data.readDouble();
	}
	
	@Override
	public byte getTagID() {
		return 6;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeDouble(data);
		return d.getBytes();
	}
	
	public double toDouble() {
		return data;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData data = new ByteData();
		data.writeDouble(this.data);
		return data.getBytes();
	}
}