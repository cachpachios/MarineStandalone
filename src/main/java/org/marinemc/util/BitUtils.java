package org.marinemc.util;

/**
 * To manipulate sertian bits in a number
 * 
 * @author Fozie
 */
public class BitUtils {
	public static boolean getBit(final byte data, final int bit) {
		return (data & bit) != 0;
	}

	public static byte setBit(final byte input, final int bit,
			final boolean status) {
		if (status)
			return (byte) (input | 1 << bit);
		else
			return (byte) (input & ~(1 << bit));
	}

	public static boolean getBit(final short data, final int bit) {
		return (data & bit) != 0;
	}

	public static short setBit(final short input, final int bit,
			final boolean status) {
		if (status)
			return (short) (input | 1 << bit);
		else
			return (short) (input & ~(1 << bit));
	}

	public static boolean getBit(final int data, final int bit) {
		return (data & bit) != 0;
	}

	public static int setBit(final int input, final int bit,
			final boolean status) {
		if (status)
			return (short) (input | 1 << bit);
		else
			return (short) (input & ~(1 << bit));
	}

	public static byte bitMap(final boolean v1, final boolean v2,
			final boolean v3, final boolean v4, final boolean v5,
			final boolean v6, final boolean v7, final boolean v8) {
		final byte r = 0;
		setBit(r, 0, v1);
		setBit(r, 1, v2);
		setBit(r, 2, v3);
		setBit(r, 3, v4);
		setBit(r, 4, v5);
		setBit(r, 5, v6);
		setBit(r, 6, v7);
		setBit(r, 7, v8);
		return r;
	}

	public static short bitMap(final boolean v1, final boolean v2,
			final boolean v3, final boolean v4, final boolean v5,
			final boolean v6, final boolean v7, final boolean v8,
			final boolean v9, final boolean v10, final boolean v11,
			final boolean v12, final boolean v13, final boolean v14,
			final boolean v15, final boolean v16) {
		final short r = 0;
		setBit(r, 0, v1);
		setBit(r, 1, v2);
		setBit(r, 2, v3);
		setBit(r, 3, v4);
		setBit(r, 4, v5);
		setBit(r, 5, v6);
		setBit(r, 6, v7);
		setBit(r, 7, v8);
		setBit(r, 8, v9);
		setBit(r, 9, v10);
		setBit(r, 10, v11);
		setBit(r, 11, v12);
		setBit(r, 12, v13);
		setBit(r, 13, v14);
		setBit(r, 14, v15);
		setBit(r, 15, v16);
		return r;
	}
}
