package com.marineapi.net;

import java.io.IOException;
import java.util.ArrayList;

import com.marineapi.Logging;
import com.marineapi.net.data.ByteData;
import com.marineapi.net.data.ByteEncoder;

public class ClientThread extends Thread{
	private Client client;
	
	boolean shouldRun;
	
	public ClientThread(Client c) {
		client = c;
	}
	
	public void run(){
		shouldRun = true;
		
		while(shouldRun) {
			
			try {
				client.getConnection().getOutputStream().write(ByteEncoder.writeBoolean(false));
			} catch (IOException e1) {
				client.getNetwork().cleanUp(client);
				Logging.getLogger().info("Client terminated at: " + client.getAdress());
				shouldRun = false;
				break;
			}
			
			
			// Read from client:
			if(client.getConnection().isClosed()) {
				client.getNetwork().cleanUp(client);
				Logging.getLogger().info("Client terminated at: " + client.getAdress());
				shouldRun = false;
				break;
			}
			
			int a = 0;
			try { a = client.getConnection().getInputStream().available(); } catch (IOException e) {
				Logging.getLogger().info("Client terminated at: " + client.getAdress());
				client.getNetwork().cleanUp(client);
				shouldRun = false;
				break;
			}
			
			
			if(a==0) {
				try {
					ClientThread.sleep(1);
				} catch (InterruptedException e) {}
				continue;
				
			}
			
			
			
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
