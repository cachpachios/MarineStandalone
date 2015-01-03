package org.marinemc.world.entity.meta;

import java.util.HashMap;
import java.util.Map;

import org.marinemc.io.binary.ByteList;

abstract class Metadata {
	private Map<Integer, MetaObject> objects;
	
	public Metadata(final int size) {
		objects = new HashMap<>(size);
	}
	
	void add(int i, MetaObject obj) {
		objects.put(i, obj);
	}
	
	public void finalAdd() {}
	
	public byte[] getBytes() {
		ByteList data = new ByteList();
		
		for(int id : objects.keySet()) {
			data.writeByte(objects.get(id).getPrefix(id));
			data.write(objects.get(id).getBytes());
		}
		data.writeByte((byte) 0x7F);
		
		return data.toBytes();
	}
	
}
