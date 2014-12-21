///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.net;

import org.marinemc.logging.Logging;
import org.marinemc.server.MarineServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
/**
 * @author Fozie
 */
public class NetworkManager {
    private final MarineServer marineServer;
    public PacketHandler packetHandler;
    public ServerSocket server;
    public ClientProcessor clientHandler;
    private Collection<Client> clientList;
    private List<Client> cleanUpList;

    private ConnectionThread connector;

    public NetworkManager(MarineServer marineServer, int port, boolean hashing) {
        this.marineServer = marineServer;

        if (hashing)
            clientList = Collections.synchronizedSet(new HashSet<Client>());
        else
            clientList = Collections.synchronizedList(new ArrayList<Client>());

        cleanUpList = new ArrayList<Client>();
        try {
            server = new ServerSocket(port, 100); //Port and num "queued" connections
        } catch (IOException e) {
            Logging.getLogger().fatal("Port binding failed, perhaps already in use");
            System.exit(1);
        }
        Logging.getLogger().log("Binding to port: §c" + port);
        if (port != 25565) {
            Logging.getLogger().warn(
                    "You are not running on the default port (§c25565§0)");
        }
        connector = new ConnectionThread(this);

        packetHandler = new PacketHandler(marineServer);

        clientHandler = new ClientProcessor(this);
    }

    public void openConnection() {
        connector.start(); // Permitt Connections
        clientHandler.start(); // Start the connection thread to intercept any packages
    }

    public Collection<Client> getClients() {
        return clientList;
    }


    public void broadcastPacket(Packet p) {
        synchronized (clientList) {
            for (Client c : clientList)
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
        clientList.add(c);
    }

    public void cleanUp(Client c) {
        if (!cleanUpList.contains(c))
            cleanUpList.add(c);
    }

    private void terminate(Client client) {
        if (client.getState() != States.INGAME)
            Logging.getLogger().info("Client Ping Terminated At: " + client.getAdress().getHostAddress() + ":" + client.getConnection().getPort());
        clientList.remove(client);
        client.terminate();
    }

    public boolean processAll() {
        synchronized (clientList) {
            boolean didProccessSomething = false;
            for (final Client c : clientList) {
                Client.ConnectionStatus status = c.process();
                if (status == Client.ConnectionStatus.CONNECTION_PROBLEMS)
                    if (c.getUID() != -1) {
                        this.marineServer.getPlayerManager().disconnect(marineServer.getPlayerManager().getPlayerByClient(c), "Client Disconnected");
                    } else {
                        cleanUp(c);
                    }
                else if (status == Client.ConnectionStatus.CLOSED) {
                    if (c.getUID() != -1) {
                        this.marineServer.getPlayerManager().disconnect(marineServer.getPlayerManager().getPlayerByClient(c), "Client Disconnected");
                    } else
                        cleanUp(c);
                }
                if (status == Client.ConnectionStatus.PROCESSED)
                    didProccessSomething = true;
            }
            for (Client c : cleanUpList)
                terminate(c);
            cleanUpList.clear();
            return didProccessSomething;
        }
    }

    public boolean hasClientsConnected() {
        return clientList.size() > 0;
    }

    public void tryConnections() {
    	synchronized(clientList) {
		for (final Client c : clientList) {
		    if (!c.isActive())
		            marineServer.getPlayerManager().disconnect(marineServer.getPlayerManager().getPlayerByClient(c), "Connection Quit");
		}}
    }

    // Client processing thread

    public class ClientProcessor extends Thread {

        private NetworkManager host;

        public ClientProcessor(NetworkManager manager) {
            super("ClientInterceptor");
            host = manager;
        }

        public void run() {
            while (true)
                if (!host.processAll())
                    if (host.hasClientsConnected())
                        try {
                            ClientProcessor.sleep(50);
                        } catch (InterruptedException e) {
                        }
                    else
                        try {
                            ClientProcessor.sleep(0, 500);
                        } catch (InterruptedException e) {
                        }
        }
    }
} 
