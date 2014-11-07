package com.marineapi.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	private final NetworkManager networkManager;
	
	private ClientThread privateHandler;
	
	private int state;
	
	private final Socket connection;
	
	public Client(NetworkManager network, Socket s) {
		this.state = 0;
		this.networkManager = network;
		this.connection = s;
	}
	
	public void sendPacket(Packet packet) {
		try {
			packet.writeToStream(connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public NetworkManager getNetwork() {
		return networkManager;
	}
	
	public InetAddress getAdress() {
		return connection.getInetAddress();
	}
	
	public Socket getConnection() {
		return connection;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public void setThread(ClientThread t) {
		privateHandler = t;
		privateHandler.start();
	}
}
