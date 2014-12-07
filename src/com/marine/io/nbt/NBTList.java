package com.marine.io.nbt;

import com.marine.io.data.ByteData;

import java.util.ArrayList;
import java.util.List;

public class NBTList implements NBTTag {

    private final String name;
    byte type;
    int size;
    List<NBTTag> data;

    public NBTList(String name, ByteData data) {
        this.name = name;
        this.data = new ArrayList<NBTTag>();
        this.type = data.readByte();
        this.size = data.readInt();

        int i = 0;
        while (i < size) {
            this.data.add(NBT.parse(data, type));
        }

    }

    @Override
    public byte getTagID() {
        return 9;
    }

    @Override
    public byte[] toByteArray() {
        ByteData data = new ByteData();

        data.writeByte(getTagID());
        data.writeUTF8Short(name);
        data.writeByte(type);
        data.writeInt(size);

        for (NBTTag tag : this.data)
            data.writeend(tag.toByteArray());


        return data.getBytes();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] toNonPrefixedByteArray() {
        ByteData data = new ByteData();
        data.writeByte(type);
        data.writeInt(size);

        for (NBTTag tag : this.data)
            data.writeend(tag.toByteArray());
        return data.getBytes();
    }
}
