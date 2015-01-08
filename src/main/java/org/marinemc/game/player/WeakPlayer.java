package org.marinemc.game.player;

import java.lang.ref.WeakReference;
import java.util.UUID;

import org.marinemc.net.Client;

/**
 * WeakReference of player, includes necessary player functions
 */
public class WeakPlayer implements IPlayer {

	WeakReference<Player> ref;

	public WeakPlayer(final Player p) {
		ref = new WeakReference<>(p);
	}

	/**
	 * Will get the referenced player
	 * 
	 * @return The player or null if it does not exist
	 */
	public Player getPlayer() {
		return ref.get();
	}

	/**
	 * @return If the players still exists in memory.
	 */
	public boolean isReferenceAlive() {
		return ref.get() != null;
	}

	/**
	 * Get the UID of the referenced player If players is disconnected, return
	 * -1.
	 * 
	 * @return The uid
	 */
	@Override
	public short getUID() {
		if (isReferenceAlive())
			return ref.get().getUID();
		else
			return -1;
	}

	/**
	 * Get the UUID of the referenced player If players is disconnected, return
	 * the uuid of Steve.
	 * 
	 * @return The uuid
	 */
	@Override
	public UUID getUUID() {
		if (isReferenceAlive())
			return ref.get().getUUID();
		else
			return UUID.fromString("ec561538-f3fd-461d-aff5-086b22154bce"); // Retrives
																			// the
																			// uuid
																			// of
																			// 'Alex'
	}

	/**
	 * Get the username of the referenced player If players is disconnected,
	 * return the uuid of Steve.
	 * 
	 * @return The username
	 */
	@Override
	public String getUserName() {
		if (isReferenceAlive())
			return ref.get().getUserName();
		else
			return "Alex";
	}

	/**
	 * Returns the client if the referenced player exists in memory, Otherwise
	 * returns null.
	 * 
	 * @return The client
	 */
	public Client getClient() {
		if (isReferenceAlive())
			return ref.get().getClient();
		else
			return null;
	}

}
