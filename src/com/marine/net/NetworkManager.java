package com.marine.net;

import com.marine.Logging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkManager {
	private List<Client> connectedClients;
	
	private List<Client> cleanUpList;
	
	public PacketHandler packetHandler;
	
	public ServerSocket server;
	
	private ConnectionThread connector;
	
	public ClientProcessor clientHandler;

	public NetworkManager(int port) {
		connectedClients = Collections.synchronizedList(new ArrayList<Client>());
		cleanUpList = Collections.synchronizedList(new ArrayList<Client>());
		try {
			server = new ServerSocket(port, 100); //Port and num "queued" connections 
		} catch (IOException e) {
			Logging.getLogger().fatal("Port binding failed, perhaps already in use");
			System.exit(1);
		}
		Logging.getLogger().log("Binding to port: §c" + port);
		connector = new ConnectionThread(this);
		
		packetHandler = new PacketHandler();
		
		clientHandler = new ClientProcessor(this);
	}
	
	public void openConnection() {
		connector.start(); // Permitt Connections
		clientHandler.start(); // Start the connection thread to intercept any packages
	}

	public List<Client> getClients() {
		return connectedClients;
	}
	
	
	public void broadcastPacket(Packet p) {
		synchronized(connectedClients) {
			for(Client c : connectedClients)
				c.sendPacket(p);
		}
	}

	public void connect(Socket accept) { 
		Client c = new Client(this, accept);
		connectedClients.add(c);
	}

	public void cleanUp(Client c) {
		cleanUpList.add(c);
	}
	
	private void terminate(Client client) {
		synchronized(cleanUpList) {
			Logging.getLogger().info("Client Terminated at: " + client.getAdress().getHostAddress()); 
			connectedClients.remove(client);
			client.terminate();
		}
	}
	
	public boolean processAll() {

		synchronized(connectedClients) {		
			if(connectedClients.isEmpty())
				return false;
	
			boolean didProccessSomething = false;
			for(Client c : connectedClients) {
			Client.ConnectionStatus status = c.process();
			if(status== Client.ConnectionStatus.CONNECTION_PROBLEMS)
				cleanUp(c);
			if(status== Client.ConnectionStatus.PROCESSED)
				didProccessSomething = true;
			
			
		}
			synchronized(cleanUpList) {
			for(Client c : cleanUpList)
				terminate(c);
			cleanUpList.clear();
			}
			
			return didProccessSomething;
		}
	}
	
	public boolean hasClientsConnected() {
		return !connectedClients.isEmpty();
	}
	
	// Client processing thread
	
	public class ClientProcessor extends Thread {
		
		private NetworkManager host;
		
		public ClientProcessor(NetworkManager manager) {
			host = manager;
		}
		
		public void run() {
			while(true) 
				if(!host.processAll()) 
					try {
						ClientProcessor.sleep(1, 100);
					} catch (InterruptedException e) {continue;}
		}
	}	
} 
