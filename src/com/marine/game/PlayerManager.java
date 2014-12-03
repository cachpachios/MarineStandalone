package com.marine.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.marine.StandaloneServer;
import com.marine.net.States;
import com.marine.net.play.clientbound.ClientboundPlayerLookPositionPacket;
import com.marine.net.play.clientbound.JoinGamePacket;
import com.marine.net.play.clientbound.MapChunkPacket;
import com.marine.net.play.clientbound.PlayerAbilitesPacket;
import com.marine.net.play.clientbound.PlayerPositionPacket;
import com.marine.net.play.clientbound.SpawnPointPacket;
import com.marine.net.play.clientbound.windows.WindowItemsPacket;
import com.marine.net.play.serverbound.ServerboundPlayerLookPositionPacket;
import com.marine.player.AbstractPlayer;
import com.marine.player.IPlayer;
import com.marine.player.Player;
import com.marine.util.Location;
import com.marine.world.Chunk;

public class PlayerManager {
	
	private List<Player> allPlayers;
	
	private Map<UUID, Player> playerIDs;
	private Map<String, Player> playerNames;
	
	
	private LoginHandler loginManager;
	
	private final StandaloneServer server;
	
	public void updateThemAll() {
		for(Player p : allPlayers)
			p.update();
	}
	
	public PlayerManager(StandaloneServer server) {
		this.server = server;
		loginManager = new LoginHandler(this, this.server.getWorldManager().getMainWorld(), this.server.getWorldManager().getMainWorld().getSpawnPoint());
		allPlayers = Collections.synchronizedList(new ArrayList<Player>());
		playerIDs = Collections.synchronizedMap(new ConcurrentHashMap<UUID, Player>());
		playerNames = Collections.synchronizedMap(new ConcurrentHashMap<String, Player>());
	}
	
	public StandaloneServer getServer() {
		return server;
	}
	
	public boolean isPlayerOnline(String name) {
		return playerNames.containsKey(name);
	}
	
	public boolean isPlayerOnline(UUID uid) {
		return playerIDs.containsKey(uid);
	}
	
	protected void putPlayer(Player p) {
		if(allPlayers.contains(p))
			return;
		
		allPlayers.add(p);
		playerIDs.put(p.getUUID(), p);
		playerNames.put(p.getName(), p);
	}
	
	public Player getPlayer(UUID uuid) {
		if(!playerIDs.containsKey(uuid))
			return null;
		return playerIDs.get(uuid);
	}
	
	public Player getPlayer(String displayName) {
		if(!playerNames.containsKey(displayName))
			return null;
		return playerNames.get(displayName);
		
	}
	
	protected void removePlayer(Player p) {
		synchronized(allPlayers){ synchronized(playerIDs){synchronized(playerNames){
			if(allPlayers.contains(p)) {
				allPlayers.remove(p);
				playerIDs.remove(p.getUUID());
				playerNames.remove(p.getName());
			}
		}}} // Sync end
	}
	
	public void tickAllPlayers() {
		synchronized(allPlayers) {
			for(IPlayer p : allPlayers)
				if(p instanceof Player) {
					Player pl = (Player) p;
					pl.tick();
			}
		}
	}

	public LoginHandler getLoginManager() {
		return loginManager;
	}

    public List<Player> getPlayers() {
        return allPlayers;
    }

	protected Player passFromLogin(IPlayer player) {
		if(player instanceof Player) {
			putPlayer((Player) player);
			return (Player) player;
		}
		else
			if(player instanceof AbstractPlayer) {
				Player p = new Player((AbstractPlayer) player, server.getGamemode());
				putPlayer(p);
				return p;
			}
		return null; // This shoulnt happening if id does its wierd :S
	}

	private void cleanUp(Player p) {
		removePlayer(p);
		//TODO: send player remove packet to every other client
		server.getNetwork().cleanUp(p.getClient());
	}
	
	protected void disconnect(Player p) {
		cleanUp(p);
	}
	
	public void joinGame(Player p) {
		if(p.getClient().getState() != States.INGAME) {
			cleanUp(p); return;
		}
	 	
		p.getClient().sendPacket(new JoinGamePacket(p));
		p.getClient().sendPacket(new SpawnPointPacket(p.getWorld().getSpawnPoint()));
		p.getClient().sendPacket(new PlayerAbilitesPacket(p.getAbilities()));
		
		p.getClient().sendPacket(new ClientboundPlayerLookPositionPacket(p.getWorld().getSpawnPoint().toLocation()));
	}
	
	public void sendLoginPackages(ServerboundPlayerLookPositionPacket packet, Player p) {
		//TODO: Check if packet is same that we sent client before.
		p.getClient().sendPacket(new WindowItemsPacket(p.getInventory()));
		
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
				chunks.add(p.getWorld().getChunk(0, 0));
		
		p.getClient().sendPacket(new MapChunkPacket(p.getWorld(), chunks));
		
		// Send initial position to spawn player
		
		p.getClient().sendPacket(new PlayerPositionPacket(new Location(p.getWorld(),0,4,0)));
	}
	
}
