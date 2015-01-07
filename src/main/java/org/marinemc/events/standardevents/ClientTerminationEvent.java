package org.marinemc.events.standardevents;

import java.net.InetAddress;

import org.marinemc.events.Event;

public class ClientTerminationEvent extends Event {
	
	public ClientTerminationEvent(final InetAddress address) {
		super("ClientTerminationEvent", false);
		this.address = address;
	}

	final InetAddress address;

	public InetAddress getAddress() {
		return address;
	}
	
	public String getHostAdress() {
		return address.getHostAddress();
	}
}
