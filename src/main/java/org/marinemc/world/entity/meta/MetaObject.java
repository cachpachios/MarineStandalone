package org.marinemc.world.entity.meta;

import org.marinemc.io.binary.ByteEncoder;
import org.marinemc.io.binary.ByteUtils;
import org.marinemc.util.vectors.Vector3f;
import org.marinemc.util.vectors.Vector3i;

public class MetaObject extends Object {
	private byte[] data;

	public final Type type;

	public MetaObject(final byte v) {
		data = ByteUtils.singleton(v);
		type = Type.BYTE;
	}

	public MetaObject(final boolean v) {
		data = ByteEncoder.writeBoolean(v);
		type = Type.BYTE;
	}

	public MetaObject(final short v) {
		data = ByteEncoder.writeShort(v);
		type = Type.SHORT;
	}

	public MetaObject(final int v) {
		data = ByteEncoder.writeInt(v);
		type = Type.INT;
	}

	public MetaObject(final float v) {
		data = ByteEncoder.writeInt(Float.floatToIntBits(v));
		type = Type.FLOAT;
	}

	public MetaObject(final String v) {
		data = ByteEncoder.writeUTF8(v);
		type = Type.UTF8;
	}

	public MetaObject(final Type type, final Object obj) {
		switch (type) {
		case BYTE:
			if (obj instanceof Boolean)
				data = ByteEncoder.writeBoolean((boolean) obj);
			else
				data = new byte[] { (Byte) obj };
		case SHORT:
			data = ByteEncoder.writeShort((short) obj);
		case INT:
			data = ByteEncoder.writeInt((int) obj);
		case FLOAT:
			data = ByteEncoder.writeFloat((float) obj);
		case UTF8:
			data = ByteEncoder.writeUTF8((String) obj);
		case INTVEC:
			data = ByteEncoder.writeVector3i((Vector3i) obj);
		case FLOATVEC:
			data = ByteEncoder.writeVector3f((Vector3f) obj);
		default:
			data = new byte[] { (byte) obj };
		}

		this.type = type;

	}

	@Override
	public void finalize() {
		data = null;
	}

	public byte getPrefix(final int pos) {
		return (byte) ((pos << 5 | type.getID() & 31) & 255);
	}

	public byte[] getBytes() {
		return data;
	}

	public static enum Type {
		BYTE(0, 1), SHORT(1, 2), INT(2, 4), FLOAT(3, 4), UTF8(4, -1), SLOT(5,
				-1), INTVEC(6, 12), FLOATVEC(7, 12);

		final int id;
		final int size; // Amount of bytes the object takes, -1 means it varies

		private Type(final int id, final int size) {
			this.id = id;
			this.size = size;
		}

		public int getID() {
			return id;
		}

	}
}
