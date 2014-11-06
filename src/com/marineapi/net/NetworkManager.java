package com.marineapi.net;

import java.util.ArrayList;
import java.util.List;

public class NetworkManager {
	public List<Client> connectedClients;
	
	public NetworkManager() {
		connectedClients = new ArrayList<Client>();
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
}
