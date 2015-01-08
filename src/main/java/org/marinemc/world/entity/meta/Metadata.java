package org.marinemc.world.entity.meta;

import java.util.HashMap;
import java.util.Map;

import org.marinemc.io.binary.ByteList;

abstract class Metadata {
	private final Map<Integer, MetaObject> objects;

	public Metadata(final int size) {
		objects = new HashMap<>(size);
	}

	void add(final int i, final MetaObject obj) {
		objects.put(i, obj);
	}

	public void finalAdd() {
	}

	public byte[] getBytes() {
		final ByteList data = new ByteList();

		for (final int id : objects.keySet()) {
			data.writeByte(objects.get(id).getPrefix(id));
			data.write(objects.get(id).getBytes());
		}
		data.writeByte((byte) 0x7F);

		return data.toBytes();
	}

}
