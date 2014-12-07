package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTFloat implements NBTTag {
    private final String name;
    private float data;

    public NBTFloat(String name, float v) {
        data = v;
        this.name = name;
    }

    public NBTFloat(String name, ByteData data) {
        this.data = data.readFloat();
        this.name = name;
    }

    @Override
    public byte getTagID() {
        return 5;
    }

    @Override
    public byte[] toByteArray() {
        ByteData d = new ByteData();
        d.writeByte(getTagID());
        d.writeUTF8Short(name);
        d.writeFloat(data);
        return d.getBytes();
    }

    public float toFloat() {
        return data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeFloat((this.data));
        return data.getBytes();
    }
}
