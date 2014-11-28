package com.marineapi.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class SimpleFileReader {
	
	File file;
	
	boolean isText;
	
	Charset encoding;
	
	public SimpleFileReader(File f) {
		isText = false;
	}
	
	public SimpleFileReader(File f, Charset encoding) {
		isText = true;
		this.encoding = encoding;
		this.file = f;
	}
	
	@SuppressWarnings("resource")
	public byte[] readBinary() throws IOException, FileNotFoundException {
		if(!file.canRead()) throw new IOException("Can't read file: " + file.getName());
		if(!file.exists()) throw new FileNotFoundException("File not found: " + file.getName());
		
		
		byte[] r = new byte[(int) file.length()];
		InputStream input = new BufferedInputStream(new FileInputStream(file));
		input.read(r);	
		return r;
		
	}
	
}
