package com.marine.io.nbt;

import java.util.ArrayList;
import java.util.List;

import com.marine.io.data.ByteData;

public class NBTList implements NBTTag{
	
	byte type;
	int size;
	private final String name;
	
	List<NBTTag> data;
	
	public NBTList(String name, ByteData data) {
		this.name = name;
		this.data = new ArrayList<NBTTag>();
		this.type = data.readByte();
		this.size = data.readInt();
		
		int i = 0;
		while(i < size) {
			this.data.add(NBT.parse(data, type));
		}
		
	}

	@Override
	public byte getTagID() {
		return 9;
	}

	@Override
	public byte[] toByteArray() {
		ByteData data = new ByteData();
		
		data.writeByte(getTagID());
		data.writeUTF8Short(name);
		data.writeByte(type);
		data.writeInt(size);
			
		for(NBTTag tag : this.data)
			data.writeend(tag.toByteArray());
		
			
		return data.getBytes();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData data = new ByteData();
		data.writeByte(type);
		data.writeInt(size);
			
		for(NBTTag tag : this.data)
			data.writeend(tag.toByteArray());
		return data.getBytes();
	}
}
