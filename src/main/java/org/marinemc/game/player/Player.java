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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.net.play.clientbound.KickPacket;
import org.marinemc.net.play.clientbound.inv.InventoryContentPacket;
import org.marinemc.net.play.clientbound.inv.InventoryOpenPacket;
import org.marinemc.net.play.clientbound.player.ClientboundPlayerLookPositionPacket;
import org.marinemc.net.play.clientbound.player.ExperiencePacket;
import org.marinemc.net.play.clientbound.player.PlayerAbilitesPacket;
import org.marinemc.net.play.clientbound.player.PlayerLookPacket;
import org.marinemc.net.play.clientbound.world.BlockChangePacket;
import org.marinemc.net.play.clientbound.world.ChunkPacket;
import org.marinemc.net.play.clientbound.world.MapChunkPacket;
import org.marinemc.net.play.clientbound.world.SpawnPointPacket;
import org.marinemc.net.play.clientbound.world.TimeUpdatePacket;
import org.marinemc.net.play.clientbound.world.UnloadChunkPacket;
import org.marinemc.server.Marine;
import org.marinemc.util.Assert;
import org.marinemc.util.Location;
import org.marinemc.util.Position;
import org.marinemc.util.StringComparison;
import org.marinemc.util.annotations.Cautious;
import org.marinemc.world.BlockID;
import org.marinemc.world.Gamemode;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.entity.EntityType;
import org.marinemc.world.entity.LivingEntity;

/**
 * The online ingame Player instance object
 * 
 * @author Fozie
 */

public class Player extends LivingEntity implements IPlayer, CommandSender {
	/*
	 * Identifier variables:
	 */
	private final short 	uid;
	private final UUID 		uuid;
	private final String	name;
	/*
	 * Network variables
	 */
	private final Client client;
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
	private Collection<Permission> permissions;
	/*
	 * Chat Stuff
	 */
	private long lastChatReset;
	private int messagesSent;
	/*
	 * Game variables:
	 */
	private boolean isFlying;
	private boolean canFly;
	private boolean isSneaking;

	private PlayerInventory inventory;
	private byte nextWindowID; // Generates a new byte for a new ID, cant be 0 when that is the standard inventory
	
	/**
	 * An list of entities spawned localy
	 * Its represented by the Entity UID
	 */
	private List<Integer> spawnedEntities;

	/**
	 * An list of chunks sent to the client
	 * Its represented by the ChunksPos encoded form
	 * (Half long is X integer, secound half is the Y Integer)
	 */
	private List<Long> loadedChunks;
	
	public Player(EntityType type, int ID, Location pos, short uid, UUID uuid,
			String name, float exp, int levels, Gamemode currentGameMode,
			float walkSpeed, float flySpeed, boolean isOp,
			boolean isFlying, boolean canFly,
			PlayerInventory inventory, final Client client) {
		super(type, ID, pos);
		this.uid = uid;
		this.uuid = uuid;
		this.name = name;
		this.exp = exp;
		this.levels = levels;
		this.currentGameMode = currentGameMode;
		this.walkSpeed = walkSpeed;
		this.flySpeed = flySpeed;
		this.permissions = new ArrayList<>(); // TODO Load this from somewhere
		this.group = PermissionManager.instance().getGroup(uuid);
		this.spawnedEntities = new ArrayList<Integer>(); // Could be an set but for integers linear search quicker than Hashing
		this.loadedChunks = new ArrayList<Long>();
		this.isFlying = isFlying;
		this.canFly = canFly;
		this.inventory = inventory;
		this.client = client;
		this.nextWindowID = Byte.MIN_VALUE;
		this.lastChatReset = System.currentTimeMillis();
		this.messagesSent = 0;
		this.isSneaking = false;
	}
	
	/**
	 * Generates a new window id to use
	 *
	 * @return A new window id (Never equal to 0)
	 */
	public byte nextWindowID() {
		return (++nextWindowID != 0) ? nextWindowID : ++nextWindowID;
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
	 * @param reason Reason (shown to player)
	 */
	public void kick(String reason) {
		this.client.sendPacket(new KickPacket(reason));
		Marine.getServer().getPlayerManager().disconnect(this);
	}

	@Override
	public int getSendDistance() {
		return 100;
	}

	@Override
	public void update() {
		//TODO
	}

	/**
	 * Set the gamemode and update it with the client

	 * @param gm The target gamemode
	 */
	public void updateGamemode(Gamemode gm) {
		Assert.notNull(gm);
		if(this.currentGameMode != gm) {
			//TODO: Send packet to client
			currentGameMode = gm;
		}
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
		this.updateExp();
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void updateWalkSpeed(float walkSpeed) {
		this.updateAbilites();
		this.walkSpeed = walkSpeed;
	}

	public float getFlySpeed() {
		return flySpeed;
	}

	public void updateFlySpeed(float flySpeed) {
		this.updateAbilites();
		this.flySpeed = flySpeed;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void updateIsFlying(boolean isFlying) {
		this.updateAbilites();
		this.isFlying = isFlying;
	}

	public boolean canFly() {
		return canFly;
	}

	public void updateCanFly(boolean canFly) {
		this.updateAbilites();
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
	public void sendMessage(String message) {
		client.sendPacket(new ChatPacket(message));
	}

	@Override
	public void sendMessage(ChatMessage message) {
		client.sendPacket(new ChatPacket(message));
	}

	@Override
	public void executeCommand(String command) {
		executeCommand(command, new String[] {});
	}

	@Override
	public void executeCommand(String command, String[] arguments) {
		final Command cmd = CommandManager.getInstance().getCommand(command);
		if (cmd == null) {
			final Collection<Command> commands = CommandManager.getInstance().getCommands();
			String extra;
			try {
				StringComparison cm = new StringComparison(command, commands.toArray());
				if (((double) cm.getBestMatchAdvanced()[0]) < .25) {
					extra = "";
				} else {
					extra = " Did you mean " + cm.getBestMatch();
				}
			} catch (final Throwable e) {
				extra = "";
			}
			sendMessage("There is no such command." + extra);
		} else {
			executeCommand(cmd, arguments);
		}
	}

	@Override
	public void executeCommand(Command command, String[] arguments) {
		Assert.notNull(command, arguments);
		if (hasPermission(command.getPermission())) {
			try {
				command.execute(this, arguments);
			} catch (final Throwable e) {
				sendMessage(ChatColor.RED + "Something went wrong when executing the command...");
				Logging.getLogger().error("Something went wrong when executing command /" + command.toString(), e);
			}
		} else {
			sendMessage("You are not permitted to use that command");
		}
	}

	@Override
	public boolean hasPermission(String permission) {
		return PermissionManager.instance().hasPermission(this, permission);
	}

	@Override
	public boolean hasPermission(Permission permission) {
		return PermissionManager.instance().hasPermission(this, permission);
	}

	public void sendAboveActionbarMessage(String message) {
        getClient().sendPacket(new ChatPacket(message, 2)); // TODO Event
	}

	public Collection<Permission> getPermissions() {
		return this.permissions;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		Assert.notNull(group);
		this.group = group;
	}
	
	public void teleport(Location relative) {
		// TODO THIS
	}

	public void sendMessageRaw(String msg) {
        getClient().sendPacket(new ChatPacket(msg, false));
	}

	public void openInventory(final Inventory inventory) {
		Assert.notNull(inventory);
		client.sendPacket(new InventoryOpenPacket(inventory));
    }

	public void setXP(float xp) {
        xp = Math.min(xp, 1.0f);
        xp = Math.max(xp, 0.0f);
        this.exp = xp;
        this.updateExp();
	}
	
	
	/**
	 * Clientside update methods:
	 */

	public PlayerInventory getInventory() {
		return inventory;
	}

	/**
	 * Sends a Experience update packet to the client with the current experience
	 */
	public void updateExp() {
		getClient().sendPacket(new ExperiencePacket(this));
	}

	/**
	 * Sends a PlayerAbilites Packet
	 */
    public void updateAbilites() {
        this.getClient().sendPacket(new PlayerAbilitesPacket(this));
	}

	public void sendCompassTarget(final Position pos) {
		Assert.notNull(pos);
		this.getClient().sendPacket(new SpawnPointPacket(pos));
    }

    public void sendInventory() {
        this.getClient().sendPacket(new InventoryContentPacket(getInventory()));
    }

    public void sendPositionAndLook() {
        this.getClient().sendPacket(new ClientboundPlayerLookPositionPacket(getLocation()));
    }

    public void sendLook() {
        this.getClient().sendPacket(new PlayerLookPacket(getLocation()));
	}

	public void sendBlockUpdate(final Position pos, final BlockID type) {
		Assert.notNull(pos, type);
		getClient().sendPacket(new BlockChangePacket(pos, type));
	}

	public boolean checkForSpam() {
		if ((System.currentTimeMillis() - lastChatReset) >= 5000) {
			lastChatReset = System.currentTimeMillis();
			messagesSent = 0;
		}
		return ++messagesSent >= 10;
	}

	public void sendTime() {
		getClient().sendPacket(new TimeUpdatePacket(this.getWorld()));
	}

	public void unloadChunk(final ChunkPos c) {
		if(loadedChunks.contains(c.encode())) {
			getClient().sendPacket(new UnloadChunkPacket(c));
			loadedChunks.remove(c.encode());
		}
	}
	
	public boolean sendChunk(final Chunk c) {
		if (loadedChunks.contains(c.getPos().encode())) {
			return false;
		}
		loadedChunks.add(c.getPos().encode());
		getClient().sendPacket(new ChunkPacket(c));
		return true;
	}

	public boolean sendChunks(final List<Chunk> chunks) {
		if (!Assert.notEmpty(chunks)) {
			return false;
		}
		for (final Chunk c : chunks) {
			if (loadedChunks.contains(c.getPos().encode()))
				chunks.remove(c);
			else {
				loadedChunks.add(c.getPos().encode());
			}
		}
		if (chunks.isEmpty()) {
			return false;
		}
		getClient().sendPacket(new MapChunkPacket(getWorld(), chunks));
		return true;
	}

	public int getNumChunksLoaded() {
		return loadedChunks.size();
	}

	@Override
	final public String toString() {
		return this.name;
	}
}