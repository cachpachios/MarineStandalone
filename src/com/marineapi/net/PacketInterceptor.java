package com.marineapi.net;

import com.marineapi.net.handshake.ClientHandshake;

public class PacketInterceptor {
	public static void income(ByteData data, Client c) {
		int ID = data.readVarInt();
		
		System.out.println("Packet ID: "+ ID);
		
		Packet handshake = new ClientHandshake();
		handshake.readFromBytes(data);
		System.out.println(handshake.toString());
	}
}
