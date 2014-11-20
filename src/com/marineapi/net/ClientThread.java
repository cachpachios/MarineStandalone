package com.marineapi.net;

import java.io.IOException;
import java.util.ArrayList;

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
			
			ArrayList<ByteData> packages = new ArrayList<ByteData>();
			boolean anotherPacket = true;
			
			
			while(anotherPacket) {
				int l = data.readVarInt();
				packages.add(data.readData(l));
				
				if(data.getReaderPos() <= data.getLength())
					anotherPacket = false;
			}
			
			for(ByteData p : packages) {
				client.getNetwork().packetHandler.intercept(p, client);
			}
			
		} // End of loop
	}
	
}
