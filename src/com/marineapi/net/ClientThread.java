package com.marineapi.net;

import java.io.IOException;
import java.util.ArrayList;

import com.marineapi.Logging;
import com.marineapi.net.data.ByteData;

public class ClientThread extends Thread{
	private Client client;
	
	
	public ClientThread(Client c) {
		client = c;
	}
	
	public void run(){
		while(!ClientThread.interrupted()) {
			// Read from client:
			
			if(!client.getConnection().isConnected()) {
				client.getNetwork().cleanUp(client);
				Logging.getLogger().info("Client terminated at: " + client.getAdress());
				this.interrupt();
			}
			
			int a = 0;
			try { a = client.getConnection().getInputStream().available(); } catch (IOException e) {}
			
			if(a==0) continue;
			
			byte[] allData = new byte[a];
			
			try { client.getConnection().getInputStream().read(allData); } catch (IOException e) {}
			
			ByteData data = new ByteData(allData);
			
			ArrayList<ByteData> packages = new ArrayList<ByteData>();
			
			
			while(true) {
				if(data.remainingBytes() == 0) {
					break;
				}
				int l = data.readVarInt();
			
				if(l == 0)
					return;
				
				packages.add(data.readData(l));
			}
			
			for(ByteData p : packages) {
				client.getNetwork().packetHandler.intercept(p, client);
			}
			
		} // End of loop
	}
	
}
