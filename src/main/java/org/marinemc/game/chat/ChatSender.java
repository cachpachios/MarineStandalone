package org.marinemc.game.chat;

public interface ChatSender {
	/**
	 * Check if the sender has an permission
	 * 
	 * @param permission
	 *            The permission to check
	 * @return If the sender has the permission
	 */
	public boolean hasPermission(String permission);
}
