package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTFloat implements NBTTag {
	private float data;
	
	public NBTFloat(float v) {
		data = v;
	}
		
	public NBTFloat(ByteData data) {
		this.data = data.readFloat();
	}
	
	@Override
	public byte getTagID() {
		return 5;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeFloat(data);
		return d.getBytes();
	}
	
	public float toFloat() {
		return data;
	}
}
