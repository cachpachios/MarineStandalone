package com.marineapi.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	private final NetworkManager networkManager;
	
	private ClientThread privateHandler;
	
	private States state;
	
	private final Socket connection;
	
	public Client(NetworkManager network, Socket s) {
		this.state = States.HANDSHAKE;
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
	
	protected Socket getConnection() {
		return connection;
	}

	public void setState(int state) {
		if(state==0)
			this.state = States.HANDSHAKE;
		else
			if(state==1)
				this.state = States.INTRODUCE;
		else
			if(state==2)
				this.state = States.LOGIN;
		else
			if(state==3)
				this.state = States.INGAME;
					
		
	}
	
	public States getState() {
		return state;
	}
	
	public void setThread(ClientThread t) {
		privateHandler = t;
		privateHandler.start();
	}
	
	public void terminate() {
		try {
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
