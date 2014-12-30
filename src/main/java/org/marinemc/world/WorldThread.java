package org.marinemc.world;

import java.lang.ref.WeakReference;

import org.marinemc.server.Marine;

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
			
			ref.get().tick();
			
			try {
				WorldThread.sleep((skipTime) - ((System.nanoTime()-startTime)/1000/1000));
			} catch (InterruptedException e) {}
		}
	}
}
