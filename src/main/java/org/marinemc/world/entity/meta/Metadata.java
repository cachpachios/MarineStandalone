package org.marinemc.world.entity.meta;

import java.util.ArrayList;
import java.util.List;

import org.marinemc.io.binary.ByteUtils;

class Metadata {
	private List<MetaObject> objects;
	
	public Metadata(final int size) {
		objects = new ArrayList<>(size);
	}
	
	void add(MetaObject obj) {
		objects.add(obj);
	}
	
	void set(int i, MetaObject obj) {
		objects.set(i, obj);
	}
	
	public void finalAdd() {}
	
	private final static byte[] endPoint = new byte[] {127};
	
	public byte[] getBytes() {
		byte[] r = new byte[0];
		int i = -1;
		for(MetaObject obj : objects) {
			ByteUtils.combine(r, new byte[] {obj.getPrefix(++i)});
			ByteUtils.combine(r, obj.getBytes());
		}
		ByteUtils.combine(r, endPoint);
		
		return r;
	}
	
}
