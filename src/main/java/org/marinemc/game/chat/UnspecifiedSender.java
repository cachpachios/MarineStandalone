package org.marinemc.game.chat;

/**
 * An static class that implements ChatSender
 * 
 * @author Fozie
 */
public final class UnspecifiedSender implements ChatSender {

	private static UnspecifiedSender instance;

	public static UnspecifiedSender getInstance() {
		if (instance == null)
			instance = new UnspecifiedSender();
		return instance;
	}

	@Override
	public boolean hasPermission(final String permission) {
		return true;
	}

}
