///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.game.player;

import org.marinemc.game.CommandManager;
import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.chat.ChatMessage;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.inventory.Inventory;
import org.marinemc.game.inventory.PlayerInventory;
import org.marinemc.game.permission.Group;
import org.marinemc.game.permission.Permission;
import org.marinemc.game.permission.PermissionManager;
import org.marinemc.logging.Logging;
import org.marinemc.net.Client;
import org.marinemc.net.packets.player.PlayerAbilitesPacket;
import org.marinemc.net.packets.player.PlayerLookPacket;
import org.marinemc.net.packets.player.PlayerLookPositionPacket;
import org.marinemc.net.packets.world.*;
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.net.play.clientbound.KickPacket;
import org.marinemc.net.play.clientbound.inv.InventoryContentPacket;
import org.marinemc.net.play.clientbound.inv.InventoryOpenPacket;
import org.marinemc.net.play.clientbound.world.entities.EntityLookMovePacket;
import org.marinemc.server.Marine;
import org.marinemc.util.Assert;
import org.marinemc.util.Location;
import org.marinemc.util.Position;
import org.marinemc.util.StringComparison;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.util.annotations.Clientside;
import org.marinemc.util.vectors.Vector3d;
import org.marinemc.world.BlockID;
import org.marinemc.world.Gamemode;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.entity.Entity;
import org.marinemc.world.entity.EntityTracker;
import org.marinemc.world.entity.EntityType;
import org.marinemc.world.entity.LivingEntity;

import java.util.*;

/**
 * The online ingame Player instance object
 * 
 * @author Fozie
 */

public class Player extends LivingEntity implements IPlayer, CommandSender,
		EntityTracker {
	/*
	 * Identifier variables:
	 */
	private final short uid;
	private final UUID uuid;
	private final String name;
	/*
	 * Network variables
	 */
	private final Client client;
	private final Collection<Permission> permissions;
	// TODO Use this:
	private final boolean isSneaking;
	private final PlayerInventory inventory;
	/**
	 * An list of entities spawned localy Its represented by the Entity UID
	 */
	private final List<Integer> spawnedEntities;
	/**
	 * An list of chunks sent to the client Its represented by the ChunksPos
	 * encoded form (Half long is X integer, secound half is the Y Integer)
	 */
	private final List<Long> loadedChunks;
	/**
	 * ENTITY TRACKING STARTS HERE:
	 */

	private final Map<Integer, Vector3d> trackingEntities;
	/*
	 * Experience variables:
	 */
	private float exp;
	private int levels;
	/*
	 * Abilities variables:
	 */
	private Gamemode currentGameMode;
	private float walkSpeed;
	private float flySpeed;
	private Group group;
	/*
	 * Chat Stuff
	 */
	private long lastChatReset;
	private int messagesSent;
								// when that is the standard inventory
	/*
	 * Game variables:
	 */
								private boolean isFlying;
	private boolean canFly;
	private byte nextWindowID; // Generates a new byte for a new ID, cant be 0

	public Player(final EntityType type, final int ID, final Location pos,
			final short uid, final UUID uuid, final String name,
			final float exp, final int levels, final Gamemode currentGameMode,
			final float walkSpeed, final float flySpeed, final boolean isOp,
			final boolean isFlying, final boolean canFly,
			final PlayerInventory inventory, final Client client) {
		super(type, ID, pos);
		this.uid = uid;
		this.uuid = uuid;
		this.name = name;
		this.exp = exp;
		this.levels = levels;
		this.currentGameMode = currentGameMode;
		this.walkSpeed = walkSpeed;
		this.flySpeed = flySpeed;
		permissions = new ArrayList<>(); // TODO Load this from somewhere
		group = PermissionManager.instance().getGroup(uuid);
		spawnedEntities = new ArrayList<Integer>(); // Could be an set but for
													// integers linear search
													// quicker than Hashing
		loadedChunks = new ArrayList<>();
		this.isFlying = isFlying;
		this.canFly = canFly;
		this.inventory = inventory;
		this.client = client;
		nextWindowID = Byte.MIN_VALUE;
		lastChatReset = System.currentTimeMillis();
		messagesSent = 0;
		isSneaking = false;
		trackingEntities = new HashMap<Integer, Vector3d>();
	}

	/**
	 * Generates a new window id to use
	 *
	 * @return A new window id (Never equal to 0)
	 */
	public byte nextWindowID() {
		return ++nextWindowID != 0 ? nextWindowID : ++nextWindowID;
	}

	@Override
	public short getUID() {
		return uid;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getUserName() {
		return name;
	}

	/**
	 * Kick a player with a specified reason
	 *
	 * @param reason
	 *            Reason (shown to player)
	 */
	public void kick(final String reason) {
		client.sendPacket(new KickPacket(reason));
		Marine.getServer().getPlayerManager().disconnect(this);
	}

	@Override
	public int getSendDistance() {
		return 100;
	}

	@Override
	public void update() {
		// TODO
	}

	/**
	 * Set the gamemode and update it with the client
	 *
	 * @param gm
	 *            The target gamemode
	 */
	public void updateGamemode(final Gamemode gm) {
		Assert.notNull(gm);
		if (currentGameMode != gm)
			// TODO: Send packet to client
			currentGameMode = gm;
	}

	/**
	 * Get the players gamemode
	 *
	 * @return current gamemode
	 */
	public Gamemode getGamemode() {
		return currentGameMode;
	}

	@Cautious
	public final Client getClient() {
		return client;
	}

	public boolean isInCreativeMode() {
		return getGamemode() == Gamemode.CREATIVE;
	}

	public float getExp() {
		return exp;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		levels = Math.min(levels, 255);
		levels = Math.max(levels, 0);
		this.levels = levels;
		updateExp();
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void updateWalkSpeed(final float walkSpeed) {
		updateAbilites();
		this.walkSpeed = walkSpeed;
	}

	public float getFlySpeed() {
		return flySpeed;
	}

	public void updateFlySpeed(final float flySpeed) {
		updateAbilites();
		this.flySpeed = flySpeed;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void updateIsFlying(final boolean isFlying) {
		updateAbilites();
		this.isFlying = isFlying;
	}

	public boolean canFly() {
		return canFly;
	}

	public void updateCanFly(final boolean canFly) {
		updateAbilites();
		this.canFly = canFly;
	}

	/**
	 * Just wrapps the client.isAcitve()
	 *
	 * @return If an client is connected
	 */
	@Cautious
	public boolean isOnline() {
		return client.isActive();
	}

	@Override
	public void sendMessage(final String message) {
		client.sendPacket(new ChatPacket(message));
	}

	@Override
	public void sendMessage(final ChatMessage message) {
		client.sendPacket(new ChatPacket(message));
	}

	@Override
	public void executeCommand(final String command) {
		executeCommand(command, new String[] {});
	}

	@Override
	public void executeCommand(final String command, final String[] arguments) {
		final Command cmd = CommandManager.getInstance().getCommand(command.substring(1));
		if (cmd == null) {
			final Collection<Command> commands = CommandManager.getInstance()
					.getCommands();
			String extra;
			try {
				final StringComparison cm = new StringComparison(command,
						commands.toArray());
				if ((double) cm.getBestMatchAdvanced()[0] < .25)
					extra = "";
				else
					extra = " Did you mean " + cm.getBestMatch();
			} catch (final Throwable e) {
				extra = "";
			}
			sendMessage("There is no such command." + extra);
		} else
			executeCommand(cmd, arguments);
	}

	@Override
	public void executeCommand(final Command command, final String[] arguments) {
		Assert.notNull(command, arguments);
		if (hasPermission(command.getPermission()))
			try {
				command.execute(this, arguments);
			} catch (final Throwable e) {
				sendMessage(ChatColor.RED
						+ "Something went wrong when executing the command...");
				Logging.getLogger().error(
						"Something went wrong when executing command /"
								+ command.toString(), e);
			}
		else {
			sendMessage("You are not permitted to use that command");
		}
	}

	@Override
	public boolean hasPermission(final String permission) {
		return PermissionManager.instance().hasPermission(this, permission);
	}
 
	@Override
	public boolean hasPermission(final Permission permission) {
		return PermissionManager.instance().hasPermission(this, permission);
	}

	public void sendAboveActionbarMessage(final String message) {
		getClient().sendPacket(new ChatPacket(message, 2)); // TODO Event
	}

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(final Group group) {
		Assert.notNull(group);
		this.group = group;
	}

	public void teleport(final Location relative) {
		// TODO THIS
	}

	public void sendMessageRaw(final String msg) {
		getClient().sendPacket(new ChatPacket(msg, false));
	}

	public void openInventory(final Inventory inventory) {
		Assert.notNull(inventory);
		client.sendPacket(new InventoryOpenPacket(inventory));
	}

	public void setXP(float xp) {
		xp = Math.min(xp, 1.0f);
		xp = Math.max(xp, 0.0f);
		exp = xp;
		updateExp();
	}

	/**
	 * Clientside update methods:
	 */

	public PlayerInventory getInventory() {
		return inventory;
	}

	/**
	 * Sends a Experience update packet to the client with the current
	 * experience
	 */
	public void updateExp() {

	}

	/**
	 * Sends a PlayerAbilites Packet
	 */
	public void updateAbilites() {
		getClient().sendPacket(new PlayerAbilitesPacket(this));
	}

	public void sendCompassTarget(final Position pos) {
		Assert.notNull(pos);
		getClient().sendPacket(new SpawnPointPacket(pos));
	}

	public void sendInventory() {
		getClient().sendPacket(new InventoryContentPacket(getInventory()));
	}

	public void sendPositionAndLook() {
		getClient().sendPacket(new PlayerLookPositionPacket(getLocation()));
	}

	public void sendLook() {
		getClient().sendPacket(new PlayerLookPacket(getLocation()));
	}

	public void sendBlockUpdate(final Position pos, final BlockID type) {
		Assert.notNull(pos, type);
		getClient().sendPacket(new BlockChangePacket(pos, type));
	}

	public boolean checkForSpam() {
		if (System.currentTimeMillis() - lastChatReset >= 5000) {
			lastChatReset = System.currentTimeMillis();
			messagesSent = 0;
		}
		return ++messagesSent >= 10;
	}

	public void sendTime() {
		getClient().sendPacket(new TimeUpdatePacket(getWorld()));
	}

	@Clientside
	public void unloadChunk(final ChunkPos c) {
		if (loadedChunks.contains(c.encode())) {
			getClient().sendPacket(new UnloadChunkPacket(c));
			loadedChunks.remove(c.encode());
		}
	}

	public boolean sendChunk(final Chunk c) {
		if (loadedChunks.contains(c.getPos().encode()))
			return false;
		loadedChunks.add(c.getPos().encode());
		c.subscribePlayer(this);
		getClient().sendPacket(new ChunkPacket(c));
		return true;
	}

	public boolean sendChunks(final List<Chunk> chunks) {
		
		List<Chunk> toSend = new ArrayList<>(chunks);
		
		for (Chunk c : chunks)
			if (loadedChunks.contains(c.getPos()))
				toSend.remove(c);
			else {
				c.subscribePlayer(this);
				loadedChunks.add(c.getPos().encode());
			}
		if (toSend.isEmpty())
			return false;
		
		getClient().sendPacket(new MapChunkPacket(getWorld(), toSend));
		
		return true;
	}

	public int getNumChunksLoaded() {
		return loadedChunks.size();
	}

	@Override
	final public String toString() {
		return name;
	}

	public void updateStreaming() {
		Marine.getServer().getPlayerManager().getWorldStreamer().asyncStreaming(uid);
	}

    public void localChunkRegion(int n) {
        localChunkRegion();
    }

	// TODO Fix unloading :p
	/**
	 * Checks if any chunks need to be sent to the client and puts a greater surronding area for generation if the players starts to move
	 */
	public void localChunkRegion() {
		final List<Long> chunksToRemove = new ArrayList<>(loadedChunks);

		int sent = 0;
		
		int chunkX = getBlockX() >> 4;
        int chunkZ = getBlockZ() >> 4;

        int radius = Marine.getServer().getViewDistance();
        for (int x = (chunkX - radius); x <= (chunkX + radius); x++) {
            for (int z = (chunkZ - radius); z <= (chunkZ + radius); z++) {
                final ChunkPos pos = new ChunkPos(x, z);
                if (loadedChunks.contains(pos.encode())) {
                	chunksToRemove.remove(pos.encode());
                } else {
                	sendChunk(Marine.getServer().getWorldManager().getMainWorld().getChunkForce(pos));
                	++sent;
                }
            }
        }

        // Tells the server to asyncly generate chunks around to ensure that the region is avalible when requested
        getWorld().generateAsyncRegion(chunkX, chunkZ, (int)(Marine.getServer().getViewDistance() * 1.5), (int)(Marine.getServer().getViewDistance() * 1.5));
        
//		 Unload any chunks outside the area
		for (Long p : chunksToRemove)
			Marine.getServer().getWorldManager().getMainWorld().getChunkForce(new ChunkPos(p)).unload(this);
	
		chunksToRemove.clear();
		System.out.println("Sent: ("+chunkX+"("+getX()+"), "+chunkZ+"("+getZ()+")) " + sent);
	}

	@Override
	public void killLocalEntity(final Entity e) {
		killLocalEntities(new Entity[] { e });
	}

	@Override
	public void killLocalEntity(final Integer e) {
		killLocalEntities(new Integer[] { e });
	}

	@Override
	public void killLocalEntities(final Entity[] e) {
		// TODO Send delete entities request or player leave if instanceof
		// player
	}

	@Override
	public void killLocalEntities(final Integer[] e) {
		// TODO Send delete entities request
	}

	@Override
	public boolean doesTrackEntity(final Entity e) {
		return trackingEntities.containsKey(e.getEntityID());
	}

	@Override
	public void updateLocalEntityMove(final Entity e, final double x,
			final double y, final double z) {
		if (trackingEntities.containsKey(e.getEntityID()))
			getClient().sendPacket(
					new EntityLookMovePacket(this, e, trackingEntities.get(e
							.getEntityID()), new Vector3d(x, y, z))); // TODO
																		// Replace
																		// this
																		// with
																		// only
																		// move
																		// packet
	}

	@Override
	public void updateLocalEntityLook(final Entity e) {
		if (trackingEntities.containsKey(e.getEntityID()))
			getClient().sendPacket(
					new EntityLookMovePacket(this, e, trackingEntities.get(e
							.getEntityID()), e.getLocation())); // TODO Replace
																// this with
																// only look
																// packet
	}

	@Override
	public void teleportLocalEntity(final Entity e, final double x,
			final double y, final double z) {
		// TODO
	}

	@Override
	public Vector3d getLastLocalySeenPosition(final Entity e) {
		if (trackingEntities.containsKey(e.getEntityID()))
			return trackingEntities.get(e.getEntityID());
		return null;
	}
	
	public int hashCode() {
		return uid;
	}
}