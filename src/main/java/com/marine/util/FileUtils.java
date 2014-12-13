///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created 2014-12-12 for MarineStandalone
 *
 * @author Citymonstret
 */
public class FileUtils {

    /**
     * Copy a file from one location to another
     *
     * @param in   Ingoing File
     * @param out  Outgoing File
     * @param size Byte Buffer Size (in bytes)
     */
    public static void copyFile(final InputStream in, final OutputStream out, final int size) {
        try {
            byte[] buffer = new byte[size];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the size of a file
     *
     * @param file File
     * @return Size of file
     */
    public static long getSize(final File file) {
        long size = 0;
        if (file.isDirectory()) {
            for (final File f : file.listFiles()) {
                if (f.isFile())
                    size += f.length();
                else
                    size += getSize(file);
            }
        } else if (file.isFile())
            size += file.length();
        return size;
    }
}
