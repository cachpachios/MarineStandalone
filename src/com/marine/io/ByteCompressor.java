package com.marine.io;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ByteCompressor {
    private static final int COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;
    
    private static final Inflater inflater = new Inflater();;
    private static final Deflater deflater = new Deflater(COMPRESSION_LEVEL);

    
    public static byte[] encode(byte[] data) {
    	deflater.setInput(data);
        byte[] compressedData = new byte[data.length];
        deflater.deflate(compressedData);
        
        deflater.reset();
        
        if (compressedData.length >= data.length) {
        	// Compression did just increase the size, consider raising threashold.
        	return data; // Return source
        }
        return compressedData;
        
    }
}
