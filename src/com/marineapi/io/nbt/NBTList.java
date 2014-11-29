package com.marineapi.io.nbt;

import java.util.ArrayList;
import java.util.List;

import com.marineapi.io.data.ByteData;

public class NBTList implements NBTTag{
	
	byte type;
	int size;
	
	List<NBTTag> data;
	
	public NBTList(ByteData data) {
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
		// TODO Auto-generated method stub
		return null;
	}
}
