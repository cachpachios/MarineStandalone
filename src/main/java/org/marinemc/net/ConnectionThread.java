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
import java.net.Socket;

import org.marinemc.logging.Logging;
/**
 * @author Fozie
 */
public class ConnectionThread extends Thread {
    private NetworkManager network;

    //TODO: SOME KIND OF DDOS PROTECTION!

    public ConnectionThread(NetworkManager manager) {
        super("ServerConnector");
        network = manager;
    }

    public void run() {
        Logging.getLogger().log("Waiting for connection...");
        // for(;;) is faster than while(true)
        for (; ; ) { //TODO: Stopping and starting!
            try {
                Socket connection = network.server.accept();
                network.connect(connection);
                ConnectionThread.sleep(10);
            } catch (InterruptedException ignored) {
            } catch (IOException e) {
                Logging.getLogger().error("Connection problems with client.");
            }

        }
    }
}
