package com.marine.util;

import java.security.MessageDigest;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class HexDigest {

    private final char[] hex;

    public HexDigest() {
        this.hex = "0123456789ABCDEF".toCharArray();
    }

    public String get(String s) throws Throwable {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(s.getBytes("UTF-8"));
        byte[] h = digest.digest();
        boolean n = (h[0] & 0x80) == 0x80;
        if (n) h = twosCompliement(h);
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

    private byte[] twosCompliement(byte[] p) {
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
