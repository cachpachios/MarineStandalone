package org.marinemc.game.async;

import java.util.ArrayDeque;
import java.util.Queue;

import org.marinemc.game.player.Player;
import org.marinemc.game.player.WeakPlayer;
import org.marinemc.server.Marine;
import org.marinemc.util.ObjectMeta;
import org.marinemc.util.wrapper.Movment;

/**
 * Updates with refresh rate of 10hz in normal cases
 * 
 * @author Fozie
 */

public class MovmentValidator extends Thread {
	// TODO

	public MovmentValidator() {
		super("MovmentValidator");
	}

	public void putForValidation(final Player p, final Movment m) {
		movmentsToCheck.add(new ObjectMeta<>(new WeakPlayer(p), m));
	}

	public volatile Queue<ObjectMeta<WeakPlayer, Movment>> movmentsToCheck;

	private void validate(final Player p, final Movment move) {

		// TODO

		return;
	}

	long time;

	@Override
	public void run() {
		movmentsToCheck = new ArrayDeque<>();

		// Synchronize this thread with the main thread
		while (Marine.getServer() == null)
			try {
				MovmentValidator.sleep(200);
			} catch (final InterruptedException e1) {
			}

		while (true) {
			if (Marine.getServer().getPlayerManager().isEmpty())
				try {
					MovmentValidator.sleep(200);
					continue;
				} catch (final InterruptedException e) {
				}

			time = System.nanoTime();

			for (final ObjectMeta<WeakPlayer, Movment> obj : movmentsToCheck)
				if (!obj.get().isReferenceAlive())
					continue;
				else
					validate(obj.get().getPlayer(), obj.getMeta());

			try {
				MovmentValidator.sleep(Math.max(0,
						100 - (System.nanoTime() - time) / 1000 / 1000));
			} catch (final InterruptedException e) {
				continue;
			}

			movmentsToCheck.clear();
		}
	}

}
