package com.marine.game.async;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.game.PlayerManager;
import com.marine.net.play.KeepAlivePacket;
import com.marine.net.play.clientbound.ChatPacket;
import com.marine.player.Player;

public class TimeoutManager extends Thread {
	private final PlayerManager players;
	
	private Map<Player, Integer> lastRecive; // Contains last recived in seconds
	private Map<Player, Integer> lastSent; // Contains last sent KeepAlivePacketID
	
	private final Random rand;
	
	public TimeoutManager(PlayerManager manager) {
		rand = new Random();
		this.players = manager;
		this.lastRecive = Collections.synchronizedMap(new ConcurrentHashMap<Player, Integer>());
		this.lastSent = Collections.synchronizedMap(new ConcurrentHashMap<Player, Integer>());
	}
	
	private long getMiliTime() {
		return (int) (System.nanoTime() / 1000 / 1000);
	}
	
	public void addPlayerToManager(Player p) {
		synchronized(lastRecive) {synchronized(lastSent) {
			lastRecive.put(p, 0);
		}}
	}
	
	private void update(Player p) {
		synchronized(lastSent) {
			if(lastSent.containsKey(p))
				lastSent.remove(p);
			
			int id = rand.nextInt();
			if(id < 0) // IF id is negative
				id *= -1; // make it possetive
			if(id == 0) // if its zero
				id++; // Add one
			
			p.getClient().sendPacket(new KeepAlivePacket(id));
			
			lastSent.put(p, id);
		}
	}
	
	
	
	
	public void cleanUp(Player p) {
		synchronized(lastRecive) {synchronized(lastSent) {
		if(lastRecive.containsKey(p))
			lastRecive.remove(p);
		if(lastSent.containsKey(p))
			lastSent.remove(p);
		}}
	}
	private void disconnect(Player p) {
		players.disconnect(p, "Connection Timed Out");
	}
	
	@Override
	public void run() { // Will update each second :D
		long lastTime = getMiliTime();
		while(true) {
			long time = getMiliTime();
			
			if(time - lastTime >= 1000) {
			synchronized(lastRecive) {synchronized(lastSent) {
				for(Player p : lastRecive.keySet()) {
					int t = lastRecive.get(p);
					
					lastRecive.remove(p);
					lastRecive.put(p, t + 1);
			
					if(t >= 10)
						disconnect(p);
				}
				
				for(Player p : players.getPlayers())
					if(!lastSent.containsKey(p)) update(p); // Send some packages
			}}
			lastTime = time;
			}

			try {
				TimeoutManager.sleep(1000 -(time - lastTime));
			} catch (InterruptedException e) {
			}
		}
	}

	public void keepAlive(Player p, int ID) { synchronized(lastSent) { synchronized(lastRecive) {
		if(!lastSent.containsKey(p))
			players.disconnect(p, "Invalid keepAliveID");
		else {
			lastSent.remove(p);
			lastRecive.remove(p);
			lastRecive.put(p, 0);
		}
	}}}
	
	
}
