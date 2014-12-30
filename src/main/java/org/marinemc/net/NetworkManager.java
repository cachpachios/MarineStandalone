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

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.marinemc.Bootstrap;
import org.marinemc.logging.Logging;
import org.marinemc.server.Marine;
/**
 * @author Fozie
 */
public class NetworkManager {
    public PacketHandler packetHandler;
    public ServerSocket server;
    public ClientProcessor clientHandler;
    private volatile List<Client> clientList;
    
    private ConnectionThread connector;

    public NetworkManager(int port, InetAddress bind) {
        clientList = new CopyOnWriteArrayList<Client>();
        
        try {
            server = new ServerSocket(port, 100, bind); //Port and num "queued" connections
        } catch (IOException e) {
            Logging.getLogger().fatal("Port binding failed, perhaps already in use");
            System.exit(1);
        }
        Logging.getLogger().logf("Binding to {0}:{1}", bind.getHostName(), port);
        if (port != 25565) {
            Logging.getLogger().warn(
                    "You are not running on the default port (§c25565§0)");
        }
        connector = new ConnectionThread(this);

        packetHandler = new PacketHandler();

        clientHandler = new ClientProcessor(this);
    }

    public void openConnection() {
        connector.start(); // Permitt Connections
        clientHandler.start(); // Start the connection thread to intercept any packages
    }

    public Collection<Client> getClients() {
        return clientList;
    }

    public boolean isEmpty() {
    	return clientList.isEmpty();
    }
    
    public void broadcastPacket(Packet p) {
    	for (final Client c : clientList)
    		c.sendPacket(p);
    }

    public void connect(Socket accept) {
    	final Client c;
        try {
            c = new Client(accept);
        } catch (IOException e) {
            return;
        }
        clientList.add(c);
    }

    public void cleanUp(Client c) {
    	terminate(c);
    }

    private void terminate(Client client) {
        if (client.getState() != States.INGAME && Bootstrap.debug())
            Logging.getLogger().debug("Client Ping Terminated At: " + client.getAdress().getHostAddress() + ":" + client.getConnection().getPort());
        else {
        	if(client.getUID() != -1)
        		Marine.getServer().getPlayerManager().removePlayer(client.getUID());
        }
        clientList.remove(client);
        client.terminate();
        
        client = null;
        
//        WeakReference<Client> r = new WeakReference<Client>(client);
//        while(r.get() != null)
//        	System.gc();
    }

    public void processAll() {
            for (final Client c : clientList) {
                Client.ConnectionStatus status = c.process();
                if (status.equals(Client.ConnectionStatus.CLOSED))
                        cleanUp(c);
            }
    }

    public boolean hasClientsConnected() {
        return clientList.size() > 0;
    }

    public void tryConnections()  {
		for (final Client c : clientList) {
		    if (!c.tryConnection())
		    		cleanUp(c);
		}
    }

    // Client processing thread

    public class ClientProcessor extends Thread {

        private NetworkManager host;

        public ClientProcessor(NetworkManager manager) {
            super("ClientInterceptor");
            host = manager;
        }

        public void run() {
            while (true) {
            	if(host.isEmpty()) {
					try {
						ClientProcessor.sleep(20);
					} catch (InterruptedException e) {}
					
					continue;
					
            	}
            	host.processAll();
            }
        }
    }
} 
