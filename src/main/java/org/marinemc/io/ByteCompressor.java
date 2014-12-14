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

package org.marinemc.io;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ByteCompressor {
    private static final int COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;
    private static final Deflater deflater = new Deflater(COMPRESSION_LEVEL);
    private static final Inflater inflater = new Inflater();

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

    public static byte[] decode(byte[] data) {
        inflater.setInput(data);
        byte[] decompressedData = new byte[data.length];
        deflater.deflate(decompressedData);
        deflater.reset();
        return decompressedData;
    }
}
