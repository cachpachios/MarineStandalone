package com.marineapi.net;

import java.io.IOException;

import com.marineapi.Logging;

public class ConnectionThread extends Thread {
	private final int sleepTime;
	private NetworkManager network;
	
	public ConnectionThread(int sleepTime, NetworkManager manager) {
		network = manager;
		this.sleepTime = sleepTime;
	}
	
	public void run() {
		
		Logging.getLogger().log("Connector started, Players can now connect.");
		
		while(true) { //TODO: Stopping and starting!
			try {
				network.connect(network.server.accept());
				ConnectionThread.sleep(sleepTime);
			} catch (InterruptedException e ) {
				// No problems!
			} catch (IOException e) {
				Logging.getLogger().error("Connetion problems with client.");
			}
			
		}
	
	}
	
}
