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

package org.marinemc.player;

import org.marinemc.Logging;
import org.marinemc.events.standardevents.LeaveEvent;
import org.marinemc.game.CommandManager;
import org.marinemc.game.PlayerManager;
import org.marinemc.game.chat.ChatComponent;
import org.marinemc.game.chat.ChatMessage;
import org.marinemc.game.chat.builder.Chat;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.inventory.Inventory;
import org.marinemc.game.inventory.PlayerInventory;
import org.marinemc.net.Client;
import org.marinemc.net.play.clientbound.ChatPacket;
import org.marinemc.net.play.clientbound.inv.InventoryContentPacket;
import org.marinemc.net.play.clientbound.inv.InventoryOpenPacket;
import org.marinemc.net.play.clientbound.player.ClientboundPlayerLookPositionPacket;
import org.marinemc.net.play.clientbound.player.ExperiencePacket;
import org.marinemc.net.play.clientbound.player.PlayerLookPacket;
import org.marinemc.net.play.clientbound.world.*;
import org.marinemc.server.Marine;
import org.marinemc.util.Location;
import org.marinemc.util.Position;
import org.marinemc.util.StringUtils;
import org.marinemc.util.TrackedLocation;
import org.marinemc.world.BlockID;
import org.marinemc.world.World;
import org.marinemc.world.chunk.Chunk;
import org.marinemc.world.chunk.ChunkPos;
import org.marinemc.world.entity.Entity;
import org.marinemc.world.entity.EntityType;

import java.util.*;
/**
 * The main ingame PlayerClass
 * Used tpo
 * 
 * @author Fozie
 */
public class Player extends Entity implements IPlayer, CommandSender {

    // PlayerManager
    private final PlayerManager manager;
    // Permissions
    private final List<String> permissions;
    // Unique ID
    private final short uid;
    // Levels
    public int levels = 0;
    // Exp
    public float exp = 0f;
    // Next Window ID
    private byte nextWindowID = 0; // Used for windows
    // Player ID
    private PlayerID id;
    // Private Inventory (should we reconstruct this or save it?)
    private PlayerInventory inventory;
    // Gamemode
    private Gamemode gamemode;
    // When gamemode should be updated
    private boolean gamemodeUpdate; //Keep tracks if gamemode have been change without the client getting the info
    // Client (connection)
    private Client connection;
    // Abilities
    private PlayerAbilities abilites;
    // Display Name
    private String displayName;
    // Player File (storage)
    private PlayerFile playerFile;
    // Loaded chunks...
    private List<Long> loadedChunks;

    public Player(final PlayerManager manager, final Client connection, final PlayerID id, final PlayerInventory inventory,
                  final int entityID, final World world, final TrackedLocation pos, final PlayerAbilities abilites, final Gamemode gamemode) {
        super(EntityType.PLAYER, entityID, world, pos);
        this.inventory = inventory;
        this.manager = manager;
        this.id = id;
        this.abilites = abilites;
        this.connection = connection;
        this.gamemode = gamemode;
        this.gamemodeUpdate = false;
        this.permissions = new ArrayList<>();
        this.displayName = id.name;
        this.uid = connection.getUID();
        try {
            this.playerFile = new PlayerFile(this);
        } catch (Exception e) {
            Logging.getLogger().error(
                    "Could not load/create player data file for: "
                            + getName());
            return;
        }
        this.loadedChunks = Collections.synchronizedList(new ArrayList<Long>());
    }

    public Player(AbstractPlayer player, Gamemode gm) {
        this(player.getServer().getPlayerManager(), player.getClient(), player.getInfo(), new PlayerInventory((byte) 0x00), Entity.generateEntityID(), player.getWorld(), new TrackedLocation(player.getLocation()), player.getAbilities(), gm);
    }

    public short getUID() {
        return this.uid;
    }

    public void teleport(Location location) {
        manager.getMovementManager().teleport(this, location);
    }

    public void loginPopulation() {
        getClient().sendPacket(new ExperiencePacket(this));
    }

    @Override
    public String getName() {
        return id.getName();
    }

    @Override
    public int getSendDistance() { // TODO Make this more dynamic and ability to use with client render distance
        return 200;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gm) {
        this.gamemode = gm;
        this.gamemodeUpdate = true;
    }

    private void updateExp() {
        getClient().sendPacket(new ExperiencePacket(this));
    }

    public int getLevels() {
        return this.levels;
    }

    public void setLevels(int levels) {
        levels = Math.min(levels, 255);
        levels = Math.max(levels, 0);
        this.levels = levels;
        this.playerFile.set("levels", levels);
        this.updateExp();
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        exp = Math.min(exp, 1.0f);
        exp = Math.max(exp, 0.0f);
        this.exp = exp;
        this.playerFile.set("exp", exp);
        this.updateExp();
    }

    public void sendAboveActionbarMessage(String message) {
        getClient().sendPacket(new ChatPacket(message, 2)); // TODO Event
    }

    @Override
    public void update() {
        if (abilites.needUpdate())
            connection.sendPacket(abilites.getPacket());

        if (gamemodeUpdate) this.gamemodeUpdate = false; //TODO Send gamemode packet :)
    }

    @Override
    public PlayerID getInfo() {
        return id;
    }

    @Override
    public Client getClient() {
        return connection;
    }

    @Override
    public UUID getUUID() {
        return id.getUUID();
    }

    @Override
    public void executeCommand(String command) {
        executeCommand(command, new String[]{});
    }

    public boolean hasDisplayName() {
        return !(this.displayName.equals(this.id.name));
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void executeCommand(String command, String[] arguments) {
        Command c = CommandManager.getInstance().getCommand(command.toLowerCase().substring(1));
        if (c == null) {
            sendMessage("There is no such command");
        } else {
            this.executeCommand(c, arguments);
        }
    }

    @Override
    public void executeCommand(Command command, String[] arguments) {
        String args = StringUtils.join(Arrays.asList(arguments), " ");
        Logging.getLogger().log(
                String.format("%s executed command: /%s",
                        getName(), command + " " + args)
        );
        command.execute(this, arguments);
    }

    public void openInventory(final Inventory inventory) {
        getClient().sendPacket(new InventoryOpenPacket(inventory));
    }

    @Override
    public boolean hasPermission(String permission) {
        if (permission.contains(permission)) return true;
        String[] parts = permission.split(".");
        if (parts.length < 2) return false;
        StringBuilder builder;
        int min = 0;
        int max = parts.length;
        while (true) {
            builder = new StringBuilder();
            if (max < 2) return false;
            for (int i = min; i < max; i++) {
                builder.append(parts[i]);
                if (i + 1 < max)
                    builder.append(".");
            }
            if (permission.contains(builder.toString() + ".*")) {
                return true;
            }
            --max;
        }
    }
    
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void sendMessage(String message) {
        getClient().sendPacket(new ChatPacket(message));
    }

    @Override
    public void sendMessage(ChatMessage message) {
        getClient().sendPacket(new ChatPacket(message));
    }

    @Override
    public void sendMessage(ChatComponent message) {
    }

    public void sendMessage(Chat chat) {
        getClient().sendPacket(new ChatPacket(chat));
    }

    public boolean isOnline() {
        return true;
    }

    public byte nextWindowID() {
        return nextWindowID++;
    }

    @Override
    public Location getLocation() {
        return this.getPosition();
    }

    @Override
    public Position getRelativePosition() {
        return this.getLocation().getRelativePosition();
    }
    
    public PlayerManager getPlayerManager() {
        return manager;
    }

    public PlayerAbilities getAbilities() {
        return abilites;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public void sendAbilites() {
        this.getClient().sendPacket(abilites.getPacket());
    }

    public void sendCompassTarget(Position pos) {
        this.getClient().sendPacket(new SpawnPointPacket(pos));
    }

    public void sendInventory() {
        this.getClient().sendPacket(new InventoryContentPacket(getInventory()));
    }

    public void sendPositionAndLook() {
        this.getClient().sendPacket(new ClientboundPlayerLookPositionPacket(getPosition()));
    }

    public void sendLook() {
        this.getClient().sendPacket(new PlayerLookPacket(getPosition()));
    }

    public void sendPosition() {
        sendPositionAndLook();
    }

    public void sendMapData(List<Chunk> chunks) {
        this.getClient().sendPacket(new MapChunkPacket(this.getWorld(), chunks));
        for (Chunk c : chunks)
            if (!loadedChunks.contains(c.getPos().encode()))
                this.loadedChunks.add(c.getPos().encode());
    }

    public void sendMapData(Chunk... chunks) {
        this.sendMapData(Arrays.asList(chunks));
    }

    public void sendChunk(Chunk c) {

    }

    public void sendTime() {
        this.getClient().sendPacket(new TimeUpdatePacket(getWorld()));
    }

    public void kick(String reason) {
        // LeaveEvent event = new LeaveEvent(this, LeaveEvent.QuitReason.KICKED);
        // Marine.getServer().callEvent(event);
        // Marine.broadcastMessage(event.getMessage().replace("%plr", getName()));
        // getClient().sendPacket(new KickPacket(
        //        (reason.length() > 0) ? reason : "Kicked"
        // ));
        // cleanup();
        Marine.getServer().getServer().getPlayerManager().disconnect(this, reason);
    }

    private void cleanup() {
        playerFile.saveFile();
    }

    public void disconnect(String reason) {
        if (reason.equals("Connection Quit")) {
            disconnect();
        } else {
            LeaveEvent event = new LeaveEvent(this, LeaveEvent.QuitReason.KICKED);
            Marine.getServer().callEvent(event);
            Marine.broadcastMessage(event.getMessage().replace("%plr", getName()).replace("%reason", reason));
            cleanup();
        }
    }

    public void timeoutDisconnect() {
        LeaveEvent event = new LeaveEvent(this, LeaveEvent.QuitReason.TIMEOUT);
        Marine.getServer().callEvent(event);
        Marine.broadcastMessage(event.getMessage().replace("%plr", getName()));
        cleanup();
    }

    public void disconnect() {
        LeaveEvent event = new LeaveEvent(this, LeaveEvent.QuitReason.NORMAL);
        Marine.getServer().callEvent(event);
        Marine.broadcastMessage(event.getMessage().replace("%plr", getName()));
        cleanup();
    }

    public void sendBlockUpdate(Position position, BlockID type) {
        getClient().sendPacket(new BlockChangePacket(position, type));
    }

    public void sendMessageRaw(String string) {
        getClient().sendPacket(new ChatPacket(string, false));
    }

	public void loadChunks(List<Chunk> chunksToLoad) {
		for(Chunk c : chunksToLoad)
			if(!loadedChunks.contains(c.getPos().encode()))
				loadedChunks.add(c.getPos().encode());
	}
	
	public void loadChunk(Chunk... chunks) {
		for(Chunk c : chunks)
			if(!loadedChunks.contains(c.getPos().encode()))
				loadedChunks.add(c.getPos().encode());
	}
	
	public void unloadChunk(Chunk c) {
		getClient().sendPacket(new UnloadChunkPacket(c.getPos()));
	}
	
	public Chunk[] getAllLoadedChunks() {
		final Chunk[] r = new Chunk[loadedChunks.size()];	
		int i = -1;
		for(final long l : loadedChunks)
			r[++i] = this.getWorld().getChunk(new ChunkPos(l));
		return r;
	}

}