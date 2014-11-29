package com.marineapi.io.nbt;

import java.io.File;
import java.io.IOException;

import com.marineapi.io.BinaryFile;
import com.marineapi.io.data.ByteData;

public class NBT {
	
	NBTCompound tag;
	
	public NBT(ByteData data) throws IOException {
		NBTTag Pretag = parse(data);
		
		if(Pretag instanceof NBTCompound) {
			throw new IOException("File was in wrong format");
		}
		else tag = (NBTCompound) Pretag;
		
	}
	
	public NBT(String name) {
		tag = new NBTCompound(name);
	}
	
	public NBT(File f) throws IOException { this(new BinaryFile(f).readGZIPBinary().getData()); }
	
	public void save(File fPath,boolean compress) throws IOException {
		ByteData data = new ByteData();
		data.writeend(tag.toByteArray());
		BinaryFile f = new BinaryFile(fPath,data);
		if(compress)
			f.writeGZIPBinary();
		else	
			f.writeBinary();
	}
	
	public static NBTTag parse(ByteData data) {
		byte id = data.readByte();
		return parse(data, id);
	}
	
	public static NBTTag parse(ByteData data, byte id) {
		if(id == 0)	 return null; else
		if(id == 1)	 return new NBTByte(data.readUTF8Short(),data); else
		if(id == 2)	 return new NBTShort(data.readUTF8Short(),data); else
		if(id == 3)	 return new NBTInteger(data.readUTF8Short(),data); else
		if(id == 4)	 return new NBTLong(data.readUTF8Short(),data); else
		if(id == 5)	 return new NBTFloat(data.readUTF8Short(),data); else
		if(id == 6)	 return new NBTDouble(data.readUTF8Short(),data); else
		if(id == 7)	 return new NBTByteArray(data.readUTF8Short(),data); else
		if(id == 8)	 return new NBTString(data.readUTF8Short(),data); else
		if(id == 9)	 return new NBTList(data.readUTF8Short(),data); 
		if(id == 10) return new NBTCompound(data.readUTF8Short(),data); else
		if(id == 11) return new NBTList(data.readUTF8Short(),data);
														
		return null;
	}
	public void setMainCompund(NBTCompound tag) {
		this.tag = tag;
	}
	
}
