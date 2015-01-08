package org.marinemc.game.async;

import java.util.ArrayList;

import org.marinemc.game.player.Player;
import org.marinemc.game.player.WeakPlayer;
import org.marinemc.server.Marine;
import org.marinemc.util.ObjectMeta;
import org.marinemc.util.wrapper.Movment;

/**
 * Updates with refreshrate of 10hz in normal cases
 * @author Fozie
 */

public class MovmentValidator extends Thread{
//TODO
	
	public MovmentValidator() {
		super("MovmentValidator");
	}
	
	public void putForValidation(Player p, Movment m) {
		movmentsToCheck.add(new ObjectMeta<>(new WeakPlayer(p), m));
	}
	
	public volatile ArrayList<ObjectMeta<WeakPlayer, Movment>> movmentsToCheck;
	
	private void validate(Player p,	Movment move) {
		
		//TODO
		
		return;
	}
	
	long time;
	public void run() {
		movmentsToCheck = new ArrayList<>();
		
		//Synchronize this thread with the main thread
		while(Marine.getServer() == null)
			try {
				MovmentValidator.sleep(200);
			} catch (InterruptedException e1) {}
		
		while(true) {
			if(Marine.getServer().getPlayerManager().isEmpty()) try { MovmentValidator.sleep(200); continue; } catch (InterruptedException e) {}
			
			time = System.nanoTime();
			
			for(ObjectMeta<WeakPlayer, Movment> obj : movmentsToCheck)
				if(!obj.get().isReferenceAlive())
					continue;
				else
					validate(obj.get().getPlayer(), obj.getMeta());
			
			try {
				MovmentValidator.sleep(Math.max(0,100 - ((System.nanoTime() - time)/1000/1000)));
			} catch (InterruptedException e) {
				continue;
			}
			
			movmentsToCheck.clear();
		}
	}
	
}
