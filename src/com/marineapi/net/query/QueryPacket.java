package com.marineapi.net.query;

import com.marineapi.net.ByteData;

public class QueryPacket {

	public ByteData data;
	
	public QueryPacket() {
		data = new ByteData();
	}
	
	public ByteData getData() {
		return data;
	}
	
	public void initQueryPacket() {
		
	}
}
