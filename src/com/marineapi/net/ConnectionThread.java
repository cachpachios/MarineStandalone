package com.marineapi.net;

import java.io.IOException;
import java.net.Socket;

import com.marineapi.Logging;

public class ConnectionThread extends Thread {
	private NetworkManager network;
	
	//TODO: SOME KIND OF DDOS PROTECTION!
	
	public ConnectionThread(NetworkManager manager) {
		network = manager;
	}
	
	public void run() {
		
		Logging.getLogger().log("Waiting for connection...");
		
		while(true) { //TODO: Stopping and starting!
			try {
				Socket connection = network.server.accept();
				network.connect(connection);
				ConnectionThread.sleep(0,100);
			} catch (InterruptedException e ) {} catch (IOException e) {
				Logging.getLogger().error("Connetion problems with client.");
			}
			
		}
	}
}
