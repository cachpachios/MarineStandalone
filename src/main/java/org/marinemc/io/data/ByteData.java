///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.io.data;

import org.marinemc.Logging;
import org.marinemc.util.Position;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ByteData implements Iterable<Byte>, Byteable {

    protected List<Byte> bytes;

    protected int readerPos;

    public ByteData(List<Byte> list) {
        this.bytes = list;
    }

    public ByteData(byte[] bytes) {
        this(wrap(bytes));
    }

    public ByteData() {
        this.bytes = new ArrayList<Byte>();
    }

    public ByteData(Byte[] b) {
        this.bytes = new ArrayList<>(Arrays.asList(b));
    }

    public final static Byte[] wrap(final byte[] array) {
        if (array == null)
            return null;
        else if (array.length == 0)
            return new Byte[]{};

        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = new Byte(array[i]);
        }
        return result;
    }

    public static byte[] unwrap(Byte[] array) {
        if (array == null)
            return null;
        else if (array.length == 0)
            return new byte[]{};

        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = (b == null ? 0 : b.byteValue());
        }
        return result;
    }

    public void writeByte(byte... b) {
        for (byte by : b)
            bytes.add(by);
    }

    public boolean readBoolean() {
        if (readByte() == 0)
            return false;
        else
            return true;
    }

    public byte readByte() {

        if (!hasBytes()) {
            Logging.getLogger().error("ByteData object is empty");
            return 0;
        }

        if (!canReadAnother()) {
            Logging.getLogger().error("ByteData ran out of bytes");
            return 0;
        }

        byte b = bytes.get(readerPos);
        readerPos++;
        return b;
    }

    public short readShort() {
        if (bytes.size() < 2)
            return 0;

        return (short) ((readByte() << 8) | (readByte() & 0xff));
    }

    public int readUnsignedShort() {
        if (bytes.size() < 2)
            return 0;

        return (((readByte() & 0xff) << 8) | (readByte() & 0xff));
    }

    public int readInt() {

        if (bytes.size() < 4)
            return 0;

        return (((readByte() & 0xff) << 24) | ((readByte() & 0xff) << 16) |
                ((readByte() & 0xff) << 8) | (readByte() & 0xff));

    }

    public long readLong() {
        if (bytes.size() < 8)
            return 0;
        return (((long) (readByte() & 0xff) << 56) |
                ((long) (readByte() & 0xff) << 48) |
                ((long) (readByte() & 0xff) << 40) |
                ((long) (readByte() & 0xff) << 32) |
                ((long) (readByte() & 0xff) << 24) |
                ((long) (readByte() & 0xff) << 16) |
                ((long) (readByte() & 0xff) << 8) |
                ((long) (readByte() & 0xff)));
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public char readChar() { // To give control of character readings in updates
        return readCharacter();
    }

    private char readCharacter() {
        if (bytes.size() < 2)
            return 0;

        return (char) ((readByte() << 8) | (readByte() & 0xff));
    }

    public byte[] readAllBytes() {
        byte[] a = new byte[bytes.size()];
        int i = 0;
        for (byte b : bytes) {
            a[i] = b;
            readerPos++;
            i++;
        }

        return a;
    }

    public byte[] getBytes() {
        int size = bytes.size();
        final byte[] out = new byte[size];
        for (int i = 0; i < size; i++)
            out[i] = bytes.get(i).byteValue();
        return out;
    }

    public int readVarInt() {
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = readByte();
            out |= (in & 0x7F) << (bytes++ * 7);
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return out;
    }

    public ByteData subData(int a, int b) {
        return new ByteData(this.bytes.subList(a, b));
    }

    public ByteData readData(int l) {
        int x = 0;
        ByteData data = new ByteData();

        while (l > x) {
            data.writeByte(readByte());
            x++;
        }

        return data;
    }

    public byte[] read(byte... input) {
        int i = 0;
        while (i < input.length) {
            input[i] = readByte();
            i++;
        }
        return input;
    }

    public byte[] readBytes(int amt) {
        byte[] r = new byte[amt];
        int i = 0;
        while (amt > i) {
            r[i] = readByte();
        }

        return r;
    }

    public boolean hasBytes() {
        return !bytes.isEmpty();
    }

    public boolean canReadAnother() {
        return getRemainingBytes() > 0;
    }

    public int remainingBytes() {
        return bytes.size() - readerPos;
    }

    public String readUTF8() {
        int l = readVarInt();
        if (l >= Short.MAX_VALUE) {
            Logging.getLogger().error("Tried to read String greater then max value!");
        }
        byte[] data = new byte[l];
        data = read(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    public String readUTF8Short() {
        int l = readShort();
        byte[] data = new byte[l];
        data = read(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    public void writeToStream(OutputStream stream) {
        try {
            for (byte b : bytes)
                stream.write(b);
        } catch (IOException e) {
        }
    }

    public void backReader(int amt) {
        this.readerPos = -amt;
        if (readerPos < 0)
            readerPos = 0;
    }

    public int getReaderPos() {
        return readerPos;
    }

    public int getRemainingBytes() {
        return bytes.size() - readerPos;
    }

    public int getLength() {
        return bytes.size();
    }

    public void writeUTF8(String v) {
        final byte[] bytes = v.getBytes(StandardCharsets.UTF_8);
        if (bytes.length >= Short.MAX_VALUE) {
            Logging.getLogger().error("Tried to write String greater then max value!");
        }
        // Write the string's length
        writeVarInt(bytes.length);
        writeend(bytes);
    }

    public void writeUTF16(String v) {
        final byte[] bytes = v.getBytes(StandardCharsets.UTF_16);
        if (bytes.length >= Short.MAX_VALUE) {
            Logging.getLogger().error("Tried to write String greater then max value!");
        }
        // Write the string's length
        writeVarInt(bytes.length);
        writeend(bytes);
    }

    public void writeASCII(String v) {
        final byte[] bytes = v.getBytes(StandardCharsets.US_ASCII);
        if (bytes.length >= Short.MAX_VALUE) {
            Logging.getLogger().error("Tried to write String greater then max value!");
        }
        // Write the string's length
        writeVarInt(bytes.length);
        writeend(bytes);
    }

    public void writeUTF8Short(String v) {
        final byte[] bytes = v.getBytes(StandardCharsets.UTF_8);
        writeShort((short) bytes.length);
        writeend(bytes);
    }

    public void writeend(byte... v) {
        for (byte b : v)
            bytes.add(b);
    }

    public void write(int pos, byte... v) {
        bytes.addAll(pos, Arrays.asList(wrap(v)));
    }

    public void writeBoolean(boolean v) {
        writeend(ByteEncoder.writeBoolean(v));
    }

    public void writeShort(short v) {
        writeend(ByteEncoder.writeShort(v));
    }

    public void writeInt(int v) {
        writeend(ByteEncoder.writeInt(v));
    }

    public void writeLong(long v) {
        writeend(ByteEncoder.writeLong(v));
    }

    public void writeFloat(float v) {
        writeend(ByteEncoder.writeFloat(v));
    }

    public void writeDouble(double v) {
        writeend(ByteEncoder.writeDouble(v));
    }

    public void writeVarInt(int v) {
        writeend(ByteEncoder.writeVarInt(v));
    }

    public boolean writeObj(Object obj) {
        if (obj instanceof Byte) {
            writeByte((byte) obj);
            return true;
        } else if (obj instanceof Short) {
            writeShort((short) obj);
            return true;
        } else if (obj instanceof Integer) {
            writeInt((int) obj);
            return true;
        } else if (obj instanceof Long) {
            writeLong((long) obj);
            return true;
        } else if (obj instanceof Float) {
            writeFloat((float) obj);
            return true;
        } else if (obj instanceof Double) {
            writeDouble((double) obj);
            return true;
        } else
            return false;

    }

    public void writeVarInt(int pos, int v) {
        write(pos, ByteEncoder.writeVarInt(v));
    }

    public void writePosition(Position pos) {
        writeend(ByteEncoder.writeLong(pos.encode()));
    }

    public Position readPosition() {
        return Position.Decode(readLong());
    }

    public void writePacketPrefix() {
        write(0, ByteEncoder.writeVarInt(bytes.size()));
    }

    @Override
    public Iterator<Byte> iterator() {
        return bytes.iterator();
    }

    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public List<Byte> getByteList() {
        return bytes;
    }

    public void writeData(ByteData data) {
        bytes.addAll(data.getByteList());
    }

    public void writeArray(Byte[] array) {
        bytes.addAll(Arrays.asList(array));
    }

    public void writeList(List<Byte> data) {
        bytes.addAll(data);
    }

    @Override
    public byte[] toBytes() {
        return unwrap((Byte[]) bytes.toArray());
    }

}