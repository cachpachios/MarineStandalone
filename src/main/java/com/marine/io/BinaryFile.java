package com.marine.io;

import com.marine.io.data.ByteData;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BinaryFile {
    File file;

    ByteData data;

    public BinaryFile(File f) {
        this.file = f;
        this.data = new ByteData();
    }

    public BinaryFile(File f, ByteData v) {
        this.data = v;
        this.file = f;
    }

    public static InputStream decompressStream(InputStream input) throws IOException {
        PushbackInputStream pb = new PushbackInputStream(input, 2); //we need a pushbackstream to look ahead
        byte[] signature = new byte[2];
        pb.read(signature); //read the signature
        pb.unread(signature); //push back the signature to the stream
        if (signature[0] == (byte) 0x1f && signature[1] == (byte) 0x8b) //check if matches standard gzip magic number
            return new GZIPInputStream(pb);
        else
            return pb;
    }

    @SuppressWarnings("resource")
    public BinaryFile readBinary() throws IOException {
        if (!file.canRead()) throw new IOException("Can't read file: " + file.getName());
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getName());

        byte[] r = new byte[(int) file.length()];
        InputStream input = new BufferedInputStream(new FileInputStream(file));
        input.read(r);
        data = new ByteData(r);
        return this;
    }

    public BinaryFile readGZIPBinary() throws IOException {
        if (!file.canRead()) throw new IOException("Can't read file: " + file.getName());
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getName());

        byte[] r = new byte[(int) file.length()];
        InputStream input = decompressStream(new BufferedInputStream(new FileInputStream(file)));
        input.read(r);
        data = new ByteData(r);
        return this;
    }

    public void writeBinary() throws IOException, FileNotFoundException {
        if (!file.exists())
            file.createNewFile();
        OutputStream output = null;
        output = new BufferedOutputStream(new FileOutputStream(file));
        output.write(data.getBytes());
        output.close();
    }

    public void writeGZIPBinary() throws IOException, FileNotFoundException {
        if (!file.exists())
            file.createNewFile();
        GZIPOutputStream output = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        output.write(data.getBytes());
        output.close();
    }

    public ByteData getData() {
        if (data == null)
            return null;
        return data;
    }
}
