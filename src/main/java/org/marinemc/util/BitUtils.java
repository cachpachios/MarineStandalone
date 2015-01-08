package org.marinemc.util;
/**
 * To manipulate sertian bits in a number
 * 
 * @author Fozie
 */
public class BitUtils {
	public static boolean getBit(byte data, int bit) {
        return (data & bit) != 0;
    }

    public static byte setBit(final byte input, final int bit, final boolean status) {
        if (status) {
            return (byte) (input | (1 << bit));
        } else {
            return (byte) (input & ~(1 << bit));
        }
    }
    
	public static boolean getBit(short data, int bit) {
        return (data & bit) != 0;
    }

    public static short setBit(short input, int bit, boolean status) {
        if (status) {
            return (short) (input | (1 << bit));
        } else {
            return (short) (input & ~(1 << bit));
        }
    }
    
	public static boolean getBit(int data, int bit) {
        return (data & bit) != 0;
    }

    public static int setBit(int input, int bit, boolean status) {
        if (status) {
            return (short) (input | (1 << bit));
        } else {
            return (short) (input & ~(1 << bit));
        }
    }
    
    public static byte bitMap(boolean v1, boolean v2, boolean v3, boolean v4, boolean v5, boolean v6, boolean v7, boolean v8) {
    	byte r = 0;
    	setBit(r,0,v1);
    	setBit(r,1,v2);
    	setBit(r,2,v3);
    	setBit(r,3,v4);
    	setBit(r,4,v5);
    	setBit(r,5,v6);
    	setBit(r,6,v7);
    	setBit(r,7,v8);
    	return r;
    }
    
    public static short bitMap(	boolean v1, boolean v2, boolean v3, boolean v4, boolean v5, boolean v6, boolean v7, boolean v8,
    							boolean v9, boolean v10, boolean v11, boolean v12, boolean v13, boolean v14, boolean v15, boolean v16) {
    	short r = 0;
    	setBit(r,0,v1);
    	setBit(r,1,v2);
    	setBit(r,2,v3);
    	setBit(r,3,v4);
    	setBit(r,4,v5);
    	setBit(r,5,v6);
    	setBit(r,6,v7);
    	setBit(r,7,v8);
    	setBit(r,8,v9);
    	setBit(r,9,v10);
    	setBit(r,10,v11);
    	setBit(r,11,v12);
    	setBit(r,12,v13);
    	setBit(r,13,v14);
    	setBit(r,14,v15);
    	setBit(r,15,v16);
    	return r;
    }
}
