package com.marine.util;

import com.marine.io.data.ByteData;

public abstract class PacketWrapper<T> {
	private T obj;
	
	public PacketWrapper(T v) {
		this.obj = v;
	}
	
	public abstract T readFromData(ByteData d);
	
	public T readFromBytes(byte[] b) {
		return readFromData(new ByteData(b));
	}
	
	public abstract ByteData toByteData();
	
	public byte[] getBytes() {
		return toByteData().getBytes();
	}
	
	public T getData() {
		return obj;
	}
	
	public void setData(T data) {
		obj = data;
	}
	
	public T get() {
		return obj;
	}
}
