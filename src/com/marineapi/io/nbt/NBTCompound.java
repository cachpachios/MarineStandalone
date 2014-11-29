package com.marineapi.io.nbt;

import java.util.ArrayList;
import java.util.List;

import com.marineapi.io.data.ByteData;

public class NBTCompound implements NBTTag {
	
	List<NBTTag> data;
	
	public NBTCompound(ByteData data) {
		this.data = new ArrayList<NBTTag>();
		byte id;
		while((id = data.readByte()) != 0) { // Add all posseble tag until Tag_END appears(id = 0)
			this.data.add(NBT.parse(data, id));
		}
	}

	@Override
	public byte getTagID() {
		return 10;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID()); // Write start ID
		
		for(NBTTag tag : data) 
			d.writeend(tag.toByteArray());
		
		d.writeByte((byte)0); // Write the TAG_END tag to tell that the compund have ended.
		
		
		return null;
	}
	
	public List<NBTTag> getTags() {
		return data;
	}
	
}
