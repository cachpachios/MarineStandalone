package com.marineapi.net;

import java.io.IOException;

import com.marineapi.net.data.ByteData;

public class ClientThread extends Thread{
	private Client client;
	
	public ClientThread(Client c) {
		client = c;
	}
	
	public void run(){

		while(true) {
			// Read from client:
			int a = 0;
			try { a = client.getConnection().getInputStream().available(); } catch (IOException e) {}
			
			if(a==0) continue;
			
			byte[] allData = new byte[a];
			
			try { client.getConnection().getInputStream().read(allData); } catch (IOException e) {}
			
			ByteData data = new ByteData(allData);
			
			int l = data.readVarInt();
			
			if(l == 0) 
				continue;
			
			PacketInterceptor.income(data, client);
		
		} // End of loop
	}
	
}
