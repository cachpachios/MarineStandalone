package com.marine;


public class MainComponent {

	public static void main(String[] args) {
		StandaloneServer server = new StandaloneServer(25565, 20); // Port and TickRate
		server.start();
	}

}
