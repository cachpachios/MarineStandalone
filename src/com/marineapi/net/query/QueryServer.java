package com.marineapi.net.query;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.marineapi.Logging;

public class QueryServer {

    DatagramSocket serverSocket;
    
    public final int PORT = 25565;
    
    public QueryServer() {
        try {
			serverSocket = new DatagramSocket(PORT);
		} catch (SocketException e) {
			Logging.getLogger().fatal("Query binding failed!");
		}
    }
	
	
	public void sendToClient(InetAddress IP, QueryPacket packet) {
        DatagramPacket sendPacket = new DatagramPacket(packet.getData().getBytes(), packet.getData().getBytes().length, IP, PORT);
        try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			Logging.getLogger().error("Unable to send Query to packet.");
		}
	}
}
