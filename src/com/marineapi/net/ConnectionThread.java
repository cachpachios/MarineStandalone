package com.marineapi.net;

import java.io.IOException;
import java.net.Socket;

import com.marineapi.Logging;

public class ConnectionThread extends Thread {
	private final int sleepTime;
	private NetworkManager network;
	
	public ConnectionThread(int sleepTime, NetworkManager manager) {
		network = manager;
		this.sleepTime = sleepTime;
	}
	
	public void run() {
		
		Logging.getLogger().log("Waiting for connection...");
		
		while(true) { //TODO: Stopping and starting!
			try {
				
				Socket connection = network.server.accept();
				network.connect(connection);
				ConnectionThread.sleep(sleepTime);
			} catch (InterruptedException e ) {
				// No point of catch exception
			} catch (IOException e) {
				Logging.getLogger().error("Connetion problems with client.");
			}
			
		}
	
	}
	
}
