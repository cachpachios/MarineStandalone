package com.marineapi.net.interceptors;

import com.marineapi.net.Client;
import com.marineapi.net.data.ByteData;

public interface PacketInterceptor {
	public boolean intercept(ByteData data, Client c);
}
