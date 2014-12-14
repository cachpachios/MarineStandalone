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

import java.security.MessageDigest;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class HexDigest {

    private final char[] hex;

    public HexDigest(char[] hexArray) {
    	this.hex = hexArray;
    }
    
    public HexDigest() {
        this("0123456789ABCDEF".toCharArray());
    }

    public String get(String s) throws Throwable {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(s.getBytes("UTF-8"));
        byte[] h = digest.digest();
        boolean n = (h[0] & 0x80) == 0x80;
        if (n) h = twosComplement(h);
        String d = getHexString(h);
        if (d.startsWith("0"))
            d = d.replaceFirst("0", d);
        if (n)
            d = "-" + d;
        return d.toLowerCase();
    }

    private String getHexString(byte[] bytes) {
        char[] hChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hChars[j * 2] = hex[v >> 4];
            hChars[j * 2 + 1] = hex[v & 0x0F];
        }
        return new String(hChars);
    }

    private byte[] twosComplement(byte[] p) {
        boolean carry = true;
        for (int x = p.length - 1; x >= 0; x--) {
            p[x] = (byte) ~p[x];
            if (carry) {
                carry = p[x] == 0xFF;
                p[x]++;
            }
        }
        return p;
    }

}
