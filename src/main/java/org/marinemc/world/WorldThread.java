package org.marinemc.world;

import java.lang.ref.WeakReference;

import org.marinemc.server.Marine;

/**
 * The world thread,
 * Does ticking and generation
 * 
 * @author Fozie
 */
public class WorldThread extends Thread {
	
	WeakReference<World> ref;
	
	public final static int skipTime = 1000 / Marine.getServer().getTickRate();
	
	public WorldThread(final World w) {
		super("WorldThread: " + w.getName());
		ref = new WeakReference<>(w);
	}
	
	
	public void run() {
		long startTime;
		while(ref.get() != null) {
			startTime = System.nanoTime();
			
			if(ref.get() == null)
				break;
			
			ref.get().tick();
			ref.get().generateRequested();
			
			
			try {
				WorldThread.sleep(nonNeg((skipTime) - ((System.nanoTime()-startTime)/1000/1000)));
			} catch (InterruptedException e) {}
		}
	}
	
	private final static long nonNeg(long l) {
		if(l > 0)
			return l;
		else
			return 0;
	}
}
