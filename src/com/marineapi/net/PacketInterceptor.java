package com.marineapi.net;

import java.io.IOException;

import com.marineapi.net.data.ByteData;
import com.marineapi.net.handshake.ClientHandshake;
import com.marineapi.net.handshake.ListPacket;
import com.marineapi.net.handshake.PingPacket;

public class PacketInterceptor { // TEMPORARY CODE, WILL MAKE IT MORE GENERIC OFC
	public static void income(ByteData data, Client c) {
		int ID = data.readVarInt();
		
		System.out.println("Packet ID: "+ ID);
		
		if(c.getState() == 0 && ID == 0) {
		ClientHandshake handshake = new ClientHandshake();
		handshake.readFromBytes(data);
		
		c.setState(handshake.getState());
		
		System.out.println(handshake.toString());
		
		c.sendPacket(new ListPacket());
		}
		else
			if(c.getState()==1 && ID==122) {
				c.sendPacket(new ListPacket());
			}
		
		else
		if(c.getState() == 1 && ID == 0x00) {
			PingPacket p = new PingPacket();
			p.readFromBytes(data);
			try {
				p.writeToStream(c.getConnection().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
