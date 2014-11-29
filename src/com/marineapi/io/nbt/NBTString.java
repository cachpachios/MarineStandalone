package com.marineapi.io.nbt;

import com.marineapi.io.data.ByteData;

public class NBTString implements NBTTag{
	
	String string;
	
	public NBTString(String v) {
		string = v;
	}

	public NBTString(ByteData data) {
		string = data.readUTF8Short();
	}
	
	@Override
	public byte getTagID() {
		return 8;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeUTF8Short(string);
		return d.getBytes();
	}
}
