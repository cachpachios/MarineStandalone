package com.marineapi.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.marineapi.Logging;

public class NetworkManager {
	public List<Client> connectedClients;
	
	public PacketHandler packetHandler;
	
	public ServerSocket server;
	
	private ConnectionThread connector;
	
	public ClientProcessor clientHandler;

	private List<Client> cleanUpList;
	
	public NetworkManager(int port) {
		connectedClients = new ArrayList<Client>();
		cleanUpList = new ArrayList<Client>();
		try {
			server = new ServerSocket(port, 100); //Port and num "queued" connections 
		} catch (IOException e) {
			Logging.getLogger().fatal("Port binding failed, perhaps allready in use");
			System.exit(1);
		}
		Logging.getLogger().log("Binding to port: " + port);
		connector = new ConnectionThread(this);
		
		packetHandler = new PacketHandler();
		
		clientHandler = new ClientProcessor(this);
	}
	
	public void openConnection() {
		connector.start(); // Permitt Connections
		clientHandler.start(); // Start the connection thread to intercept any packages
	}

	
	
	public List<Client> getClientList() {
		return connectedClients;
	}
	
	public Client[] getClients() {
		return (Client[]) connectedClients.toArray();
	}
	
	public void broadcastPacket(Packet p) {
		for(Client c : connectedClients)
			c.sendPacket(p);
	}

	public void connect(Socket accept) { 
		Client c = new Client(this, accept);
		connectedClients.add(c);
	}

	public void cleanUp(Client client) {
		cleanUpList.add(client);
	}
	
	private void doCleanUpOn(Client client) {
		Logging.getLogger().info("Client Terminated at: " + client.getAdress().getHostAddress()); 
		connectedClients.remove(client);
		client.terminate();
	}
	
	public boolean processAll() {
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
		
		for(Client c : cleanUpList)
			doCleanUpOn(c);
		
		cleanUpList.clear();
		return didProccessSomething;
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
			while(true) {
				if(!host.processAll())
					try {
						ClientProcessor.sleep(1);
					} catch (InterruptedException e) {}
			}
		}
		
	}
	
} 
