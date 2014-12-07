package com.marine.io.nbt;

import com.marine.io.data.ByteData;

public class NBTByteArray implements NBTTag {

    private final String name;
    private ByteData array;

    public NBTByteArray(String name, ByteData data) {
        this.name = name;
        int l = data.readInt();
        array = data.readData(l);
    }

    public NBTByteArray(String name, byte[] v) {
        this.name = name;
        array = new ByteData(v);
    }

    @Override
    public byte getTagID() {
        return 7;
    }

    @Override
    public byte[] toByteArray() {
        ByteData d = new ByteData();
        d.writeByte(getTagID());
        d.writeUTF8Short(getName());
        d.writeInt(array.getLength());
        d.writeend(array.getBytes());
        return d.getBytes();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeInt(array.getLength());
        data.writeend(array.getBytes());
        return data.getBytes();
    }
}
