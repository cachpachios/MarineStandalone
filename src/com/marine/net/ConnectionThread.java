package com.marine.net;

import com.marine.Logging;

import java.io.IOException;
import java.net.Socket;

public class ConnectionThread extends Thread {
    private NetworkManager network;

    //TODO: SOME KIND OF DDOS PROTECTION!

    public ConnectionThread(NetworkManager manager) {
        network = manager;
    }

    public void run() {

        Logging.getLogger().log("Waiting for connection...");

        while (true) { //TODO: Stopping and starting!
            try {
                Socket connection = network.server.accept();
                network.connect(connection);
                ConnectionThread.sleep(10);
            } catch (InterruptedException e) {
            } catch (IOException e) {
                Logging.getLogger().error("Connetion problems with client.");
            }

        }
    }
}
