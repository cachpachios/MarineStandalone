package com.marineapi.net.interceptors;

import com.marineapi.io.data.ByteData;
import com.marineapi.net.Client;

public interface PacketInterceptor {
	public boolean intercept(ByteData data, Client c);
}
