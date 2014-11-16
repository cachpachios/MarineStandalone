package com.marineapi.json.objects;

import com.marineapi.json.JSONObject;

public class JSONString extends JSONObject {
	
	String value;
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public void fromString(String s) {

	}

}
