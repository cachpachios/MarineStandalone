package com.marine;

import java.util.Arrays;


public class MainComponent {

	public static void main(String[] args) {
		StandaloneServer server = new StandaloneServer(25565, 20); // Port and TickRate
		
		short x = (short) (32000 * 2);
		
		String s2 = String.format("%8s", Integer.toBinaryString(x & 0xFFFF  & 65535)).replace(' ', '0');
		System.out.println(s2 + " = "+ (x & 65535));
		
		if(!Arrays.asList(args).contains("nogui")) // Check if GUI should'nt be shown
			Logging.getLogger().createConsoleWindow();
		
		server.start();
	}

}
