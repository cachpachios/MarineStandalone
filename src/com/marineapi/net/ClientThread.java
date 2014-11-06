package com.marineapi.net;

import java.io.IOException;

import com.marineapi.Logging;

public class ClientThread extends Thread{
	private Client client;
	
	public ClientThread(Client c) {
		client = c;
	}
	
	public void run() {
		while(true) {
			byte[] lengthRAW = new byte[4]; 
			
			try {
				if(client.getConnection().getInputStream().available() == 0) {
					continue;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				client.getConnection().getInputStream().read(lengthRAW);
			} catch (IOException e) {
				Logging.getLogger().log("InputStream error at client: " + client.getAdress());
			}
			
			int packetLength = new ByteData(lengthRAW).readVarInt();
			
			System.out.println("L:" + packetLength);
			
			byte[] d = new byte[packetLength]; 
			try {
				client.getConnection().getInputStream().read(d);
			} catch (IOException e) {
				Logging.getLogger().log("InputStream error for client: " + client.getAdress());
			}
			
			PacketInterceptor.income(new ByteData(d), client);
			
		} // End of loop
	}
	
}
