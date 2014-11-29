package com.marineapi.io.nbt;

import java.util.List;

import com.marineapi.io.data.ByteData;

public class NBTCompound implements NBTTag {
	
	List<NBTTag> data;
	
	public NBTCompound(ByteData data) {
		
	}

	@Override
	public byte getTagID() {
		return 10;
	}

	@Override
	public byte[] toByteArray() {
		ByteData d = new ByteData();
		d.writeByte(getTagID());
		
		for(NBTTag tag : data) 
			d.writeend(tag.toByteArray());
		
		d.writeByte((byte)0); // Write the TAG_END tag to tell that the compund have ended.
		
		
		return null;
	}
	
}
