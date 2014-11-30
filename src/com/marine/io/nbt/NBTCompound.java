package com.marine.io.nbt;

import java.util.ArrayList;
import java.util.List;

import com.marine.io.data.ByteData;

public class NBTCompound implements NBTTag {
	
	List<NBTTag> data;
	
	private final String name;
	
	public NBTCompound(String name, ByteData data) {
		this(name);
		byte id;
		while((id = data.readByte()) != 0) { // Add all posseble tag until Tag_END appears(d = 0)
			this.data.add(NBT.parse(data, id));
		}
	}

	public NBTCompound(String name) {
		this.name = name;
		this.data = new ArrayList<NBTTag>();
	}
	
	@Override
	public byte getTagID() {
		return 10;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID()); // Write start ID
		d.writeUTF8Short(name);
		for(NBTTag tag : data) 
			d.writeend(tag.toByteArray());
		
		d.writeByte((byte)0); // Write the TAG_END tag to tell that the compund have ended.
		
		
		return d.getBytes();
	}
	
	public void addTag(NBTTag tag) {
		data.add(tag);
	}
	
	public List<NBTTag> getTags() {
		return data;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public byte[] toNonPrefixedByteArray() {
		ByteData data = new ByteData();
		for(NBTTag tag : this.data) 
			data.writeend(tag.toByteArray());
		return data.getBytes();
	}
}
