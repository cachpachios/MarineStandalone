package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTDouble  implements NBTTag{
	private double data;
		
	public NBTDouble(double v) {
		data = v;
	}
		
	public NBTDouble(ByteData data) {
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
		d.writeDouble(data);
		return d.getBytes();
	}
	
	public double toDouble() {
		return data;
	}
}