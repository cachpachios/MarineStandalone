package com.marine.io.nbt;


public interface NBTTag {

    public String getName();

    public byte getTagID();

    public byte[] toByteArray();

    public byte[] toNonPrefixedByteArray();

}
