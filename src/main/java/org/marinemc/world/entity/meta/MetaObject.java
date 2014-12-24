package org.marinemc.world.entity.meta;

import java.nio.charset.StandardCharsets;

import org.marinemc.io.binary.ByteData;
import org.marinemc.io.data.ByteUtils;
import org.marinemc.util.vectors.Vector3f;
import org.marinemc.util.vectors.Vector3i;

public class MetaObject extends Object{
	private byte[] data;
	
	public final Type type;
	
	public MetaObject(Object obj) {
		Type type;
		if(obj instanceof Byte) type = Type.BYTE; else
			if(obj instanceof Boolean) type = Type.BYTE; else
				if(obj instanceof Short) type = Type.SHORT; else
					if(obj instanceof Integer) type = Type.INT; else
						if(obj instanceof Float) type = Type.FLOAT; else
							if(obj instanceof String) type = Type.UTF8; else
								if(obj instanceof Vector3i) type = Type.INTVEC; else
									if(obj instanceof Vector3f) type = Type.FLOATVEC; else
										throw new ClassCastException("Invalid object type!");
		switch(type) {
		case BYTE: if(obj instanceof Boolean) data = new byte[] {encodeBoolean((Boolean) obj)}; else data = new byte[] {(Byte) obj };
		case SHORT: data = parse((Short) obj);
		case INT: data = parse((Integer) obj);
		case FLOAT: data = parse((Float) obj);
		case UTF8: data = ByteUtils.combine(parseV(obj.toString().length()), obj.toString().getBytes(StandardCharsets.UTF_8));
		case INTVEC: data = ByteUtils.merge(parse(((Vector3i) obj).getX()), parse(((Vector3i) obj).getY()), parse(((Vector3i) obj).getZ()));
		case FLOATVEC: data = ByteUtils.merge(parse(((Vector3f) obj).getX()), parse(((Vector3f) obj).getY()), parse(((Vector3f) obj).getZ()));
		default: data = new byte[] {(byte)obj};
		}
		
		this.type = type;
	}
	
	private byte encodeBoolean(boolean b) {
		if(b)
			return 0x01;
		else
			return 0x00;
	}
	
	public MetaObject(Type type, Object obj) {
		switch(type) {
		case BYTE: data = new byte[] {(byte)obj};
		case SHORT: data = parse((short) obj);
		case INT: data = parse((int) obj);
		case FLOAT: data = parse((float) obj);
		case UTF8: data = ByteUtils.combine(parseV(obj.toString().length()), obj.toString().getBytes(StandardCharsets.UTF_8));
		case INTVEC: data = ByteUtils.merge(parse(((Vector3i) obj).getX()), parse(((Vector3i) obj).getY()), parse(((Vector3i) obj).getZ()));
		case FLOATVEC: data = ByteUtils.merge(parse(((Vector3f) obj).getX()), parse(((Vector3f) obj).getY()), parse(((Vector3f) obj).getZ()));
		default: data = new byte[] {(byte)obj};
		}
		
		this.type = type;
		
	}
	
    private static byte[] parse(short v) {
        return new byte[]{
                (byte) (0xff & (v >> 8)),
                (byte) (0xff & v)
        };
    } 
    
    public static byte[] parse(int v) {
        return new byte[]{
                (byte) (0xff & (v >> 24)),
                (byte) (0xff & (v >> 16)),
                (byte) (0xff & (v >> 8)),
                (byte) (0xff & v)
        };
    }
    
    public static byte[] parse(float v) {
    	return parse(Float.floatToIntBits(v));
    }
    
    private static byte[] parseV(int v) {
        ByteData r = new ByteData();

        byte part;
        while (true) {
            part = (byte) (v & 0x7F);
            v >>>= 7;
            if (v != 0) {
                part |= 0x80;
            }
            r.writeByte(part);
            if (v == 0) {
                break;
            }
        }

        return r.getBytes();
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
