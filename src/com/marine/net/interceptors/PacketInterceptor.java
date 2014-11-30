package com.marine.net.interceptors;

import com.marine.io.data.ByteData;
import com.marine.net.Client;

public interface PacketInterceptor {
	public boolean intercept(ByteData data, Client c);
}
