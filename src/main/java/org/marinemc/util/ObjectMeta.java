package org.marinemc.util;

public class ObjectMeta<O extends Object, M extends Object> {
	private final O object;

	private final M metaobject;

	public ObjectMeta(final O obj, final M meta) {
		this.object = obj;
		this.metaobject = meta;
	}

	public O get() {
		return object;
	}

	public M getMeta() {
		return metaobject;
	}

}
