package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTString implements NBTTag{
	
	String string;
	
	String name;
	
	public NBTString(String name, String v) {
		this.name = name;
		string = v;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public NBTString(String name, ByteData data) {
		this(name, data.readUTF8Short());
	}
	
	@Override
	public byte getTagID() {
		return 8;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeUTF8Short(string);
		return d.getBytes();
	}
	
	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData data = new ByteData();
		data.writeUTF8Short(this.string);
		return data.getBytes();
	}
	
}
