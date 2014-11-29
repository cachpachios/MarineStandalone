package com.marineapi.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.marineapi.io.data.ByteData;

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
	
	@SuppressWarnings("resource")
	public BinaryFile readBinary() throws IOException, FileNotFoundException {
		if(!file.canRead()) throw new IOException("Can't read file: " + file.getName());
		if(!file.exists()) throw new FileNotFoundException("File not found: " + file.getName());
		
		
		byte[] r = new byte[(int) file.length()];
		InputStream input = new BufferedInputStream(new FileInputStream(file));
		input.read(r);	
		data = new ByteData(r);
		return this;
	}
	
	public void writeBinary() throws IOException, FileNotFoundException {
			  if(!file.exists())
			  file.createNewFile();
		      OutputStream output = null;
		      output = new BufferedOutputStream(new FileOutputStream(file));
		      output.write(data.getBytes());
		      output.close();
	}
	
	public ByteData getData() {
		if(data == null)
			return null;
		return data;
	}
	
}
