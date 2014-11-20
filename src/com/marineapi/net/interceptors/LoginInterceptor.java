package com.marineapi.net.interceptors;

import com.marineapi.net.Client;
import com.marineapi.net.data.ByteData;

public class LoginInterceptor implements PacketInterceptor {

	@Override
	public boolean intercept(ByteData data, Client c) {
		return false;
	}

}
