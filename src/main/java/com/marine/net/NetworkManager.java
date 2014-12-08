package com.marine.net;

import com.marine.Logging;
import com.marine.StandaloneServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkManager {
    private final StandaloneServer marineServer;
    public PacketHandler packetHandler;
    public ServerSocket server;
    public ClientProcessor clientHandler;
    private List<Client> connectedClients;
    private List<Client> cleanUpList;
    private ConnectionThread connector;

    public NetworkManager(StandaloneServer marineServer, int port) {
        this.marineServer = marineServer;
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

        packetHandler = new PacketHandler(marineServer);

        clientHandler = new ClientProcessor(this);
    }

    public StandaloneServer getServer() {
        return marineServer;
    }

    public void openConnection() {
        connector.start(); // Permitt Connections
        clientHandler.start(); // Start the connection thread to intercept any packages
    }

    public List<Client> getClients() {
        return connectedClients;
    }


    public void broadcastPacket(Packet p) {
        synchronized (connectedClients) {
            for (Client c : connectedClients)
                c.sendPacket(p);
        }
    }

    public void connect(Socket accept) {
        Client c;
        try {
            c = new Client(this, accept);
        } catch (IOException e) {
            return;
        }
        connectedClients.add(c);
    }

    public void cleanUp(Client c) {
        if (!cleanUpList.contains(c))
            cleanUpList.add(c);
    }

    private void terminate(Client client) {
        synchronized (cleanUpList) {
            if (client.getState() != States.INGAME)
                Logging.getLogger().info("Client Ping Terminated At: " + client.getAdress().getHostAddress());
            connectedClients.remove(client);
            client.terminate();
        }
    }

    public boolean processAll() {
        synchronized (connectedClients) {
            if (connectedClients.isEmpty())
                return false;

            boolean didProccessSomething = false;
            for (Client c : connectedClients) {
                Client.ConnectionStatus status = c.process();
                if (status == Client.ConnectionStatus.CONNECTION_PROBLEMS)
                	if(c.getUserName() != null) {
                		this.marineServer.getPlayerManager().disconnect(marineServer.getPlayerManager().getPlayerByClient(c),"Client Disconnected");
                	}
                	else
                		cleanUp(c);
                else
                if(status == Client.ConnectionStatus.CLOSED) {
                	if(c.getUserName() != null) {
                		this.marineServer.getPlayerManager().disconnect(marineServer.getPlayerManager().getPlayerByClient(c),"Client Disconnected");
                	}
                	else
                		cleanUp(c);
                }
                if (status == Client.ConnectionStatus.PROCESSED)
                    didProccessSomething = true;


            }
            synchronized (cleanUpList) {
                for (Client c : cleanUpList)
                	if(connectedClients.contains(c))
                		terminate(c);
                cleanUpList.clear();
            }

            return didProccessSomething;
        }
    }

    public boolean hasClientsConnected() {
    	synchronized(connectedClients) {
    		return !connectedClients.isEmpty();
    	}
    }

    public void tryConnections() {
    	if(hasClientsConnected())
    	synchronized(connectedClients) {
    	for(Client c: connectedClients) {
    		if(!c.isActive())  {
    			if(c.getUserName() != null)
    				marineServer.getPlayerManager().disconnect(marineServer.getPlayerManager().getPlayerByClient(c),"Connection Quit");
    			cleanUp(c);
    		}
    	}
    }}
    
    // Client processing thread

    public class ClientProcessor extends Thread {

        private NetworkManager host;

        public ClientProcessor(NetworkManager manager) {
            host = manager;
        }

        public void run() {
            while (true)
                if (!host.processAll())
                    try {
                        ClientProcessor.sleep(1, 100);
                    } catch (InterruptedException e) {
                        continue;
                    }
        }
    }
} 