package org.marinemc.io.binary;

public class ByteUtils {
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
}
