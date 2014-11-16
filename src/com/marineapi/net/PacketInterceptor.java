package com.marineapi.net;

import com.marineapi.Logging;
import com.marineapi.net.data.ByteData;


public class PacketInterceptor {
	public static void income(ByteData data, Client c) {
		int id = data.readVarInt();
		
		Logging.getLogger().debug("Intercepted packet (ID: " + id +") from client " + c.getAdress().toString() + " with data length " + data.getRemainingBytes());
		
		
		
		
	}
}
