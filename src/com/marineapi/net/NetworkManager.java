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
	

	public NetworkManager(int port) {
		connectedClients = new ArrayList<Client>();
		try {
			server = new ServerSocket(port, 100); //Port and num "queued" connections 
		} catch (IOException e) {
			Logging.getLogger().fatal("Port binding failed, perhaps allready in use");
			System.exit(1);
		}
		Logging.getLogger().log("Binding to port: " + port);
		connector = new ConnectionThread(2, this);
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		packetHandler = new PacketHandler();
		
	}
	
	public void openConnection() {
		connector.start(); // Permitt Connections
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
		Logging.getLogger().log("Client: " + accept.getInetAddress().toString() + " connected");
		Logging.getLogger().log("" + connectedClients.size());
		ClientThread t = new ClientThread(c);
		c.setThread(t);
		connectedClients.add(c);
	}

	public void cleanUp(Client client) {
		connectedClients.remove(client);
		client.terminate();
	}
	
}
