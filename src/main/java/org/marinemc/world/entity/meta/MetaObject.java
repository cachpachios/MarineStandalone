package org.marinemc.world.entity.meta;

import org.marinemc.io.binary.ByteEncoder;
import org.marinemc.io.binary.ByteUtils;
import org.marinemc.util.vectors.Vector3f;
import org.marinemc.util.vectors.Vector3i;

public class MetaObject extends Object{
	private byte[] data;
	
	public final Type type;
	
	public MetaObject(byte v) {
		this.data = ByteUtils.singleton(v);
		this.type = Type.BYTE;
	}
	
	public MetaObject(boolean v) {
		this.data = ByteEncoder.writeBoolean(v);
		this.type = Type.BYTE;
	}
	
	public MetaObject(short v) {
		this.data = ByteEncoder.writeShort(v);
		this.type = Type.SHORT;
	}
	
	public MetaObject(int v) {
		this.data = ByteEncoder.writeInt(v);
		this.type = Type.INT;
	}
	
	public MetaObject(float v) {
		this.data = ByteEncoder.writeInt(Float.floatToIntBits(v));
		this.type = Type.FLOAT;
	}
	
	public MetaObject(String v) {
		this.data = ByteEncoder.writeUTF8(v);
		this.type = Type.UTF8;
	}
	
	
	public MetaObject(Type type, Object obj) {
		switch(type) {
		case BYTE: if(obj instanceof Boolean) data = ByteEncoder.writeBoolean((boolean) obj); else data = new byte[] {(Byte) obj};
		case SHORT: data = ByteEncoder.writeShort((short) obj);
		case INT: data = ByteEncoder.writeInt((int) obj);
		case FLOAT: data = ByteEncoder.writeFloat((float) obj);
		case UTF8: data = ByteEncoder.writeUTF8((String) obj);
		case INTVEC: data = ByteEncoder.writeVector3i((Vector3i) obj);
		case FLOATVEC: data = ByteEncoder.writeVector3f((Vector3f) obj);
		default: data = new byte[] {(byte)obj};
		}
		
		this.type = type;
		
	}
    
    public void finalize() {
    	data = null;
    }
    
    public byte getPrefix(int pos) {
    	return (byte) ((pos << 5 | type.getID() & 31) & 255);
    }
    
    public byte[] getBytes() {
    	return data;
    }
	
	public static enum Type {
		BYTE	(0,1),
		SHORT	(1,2),
		INT		(2,4),
		FLOAT	(3,4),
		UTF8	(4,-1),
		SLOT	(5,-1),
		INTVEC	(6,12),
		FLOATVEC(7,12);
		
		
		final int id;
		final int size; // Amount of bytes the object takes, -1 means it varies
		
		private Type(int id, int size) {
			this.id = id;
			this.size = size;
		}
		
		public int getID() {
			return id;
		}
		
	}
}
