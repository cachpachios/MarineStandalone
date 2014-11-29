package com.marineapi.io.nbt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.marineapi.io.BinaryFile;
import com.marineapi.io.data.ByteData;

public class NBT {
	
	List<NBTTag> tags;
	
	public NBT(ByteData data) {
		while(data.canReadAnother()) {
			NBTTag tag = parse(data);
			if(tag != null)
				tags.add(tag);
		}
	}
	
	public NBT(File f) throws IOException { this(new BinaryFile(f).readBinary().getData()); }
	
	public void save(File fPath) throws FileNotFoundException, IOException {
		ByteData data = new ByteData();
		for(NBTTag tag : tags)
			data.writeend(tag.toByteArray());
		
		BinaryFile f = new BinaryFile(fPath,data);
		f.writeBinary();
		
	}
	
	public static NBTTag parse(ByteData data) {
		byte id = data.readByte();
		return parse(data, id);
	}
	
	public static NBTTag parse(ByteData data, byte id) {

		if(id == 0)	 return new NBTByte((byte) 0); else
		if(id == 1)	 return new NBTByte(data); else
		if(id == 2)	 return new NBTShort(data); else
		if(id == 3)	 return new NBTInteger(data); else
		if(id == 4)	 return new NBTLong(data); else
		if(id == 5)	 return new NBTFloat(data); else
		if(id == 6)	 return new NBTDouble(data); else
		if(id == 7)	 return new NBTByteArray(data); else
		if(id == 8)	 return new NBTString(data); else
		if(id == 9)	 return new NBTList(data); 
		if(id == 10) return new NBTByte(data); else
		if(id == 11) return new NBTByte(data);
														
		return null;
	}
	public void addTag(NBTTag tag) {
		tags.add(tag);
	}
	
}
