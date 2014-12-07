package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTByte implements NBTTag {

    private final String name;
    private byte data;

    public NBTByte(String name, Byte v) {
        this.name = name;
        data = v;
    }

    public NBTByte(String name, ByteData data) {
        this(name, data.readByte());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte getTagID() {
        return 1;
    }

    @Override
    public byte[] toByteArray() {
        ByteData d = new ByteData();
        d.writeByte(getTagID());
        d.writeUTF8Short(name);
        d.writeByte(data);
        return d.getBytes();
    }

    public byte toByte() {
        return data;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeByte((this.data));
        return data.getBytes();
    }

}
