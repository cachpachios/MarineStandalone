package com.marine.player;

import com.marine.Logging;
import com.marine.events.standardevents.LeaveEvent;
import com.marine.game.CommandManager;
import com.marine.game.PlayerManager;
import com.marine.game.chat.ChatComponent;
import com.marine.game.chat.ChatMessage;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.game.inventory.Inventory;
import com.marine.game.inventory.PlayerInventory;
import com.marine.net.Client;
import com.marine.net.play.clientbound.ChatPacket;
import com.marine.net.play.clientbound.KickPacket;
import com.marine.net.play.clientbound.inv.InventoryContentPacket;
import com.marine.net.play.clientbound.inv.InventoryOpenPacket;
import com.marine.net.play.clientbound.player.ClientboundPlayerLookPositionPacket;
import com.marine.net.play.clientbound.player.ExperiencePacket;
import com.marine.net.play.clientbound.player.PlayerLookPacket;
import com.marine.net.play.clientbound.player.PlayerPositionPacket;
import com.marine.net.play.clientbound.world.MapChunkPacket;
import com.marine.net.play.clientbound.world.SpawnPointPacket;
import com.marine.net.play.clientbound.world.TimeUpdatePacket;
import com.marine.server.Marine;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.util.StringUtils;
import com.marine.world.World;
import com.marine.world.chunk.Chunk;
import com.marine.world.entity.Entity;
import com.marine.world.entity.EntityType;

import java.util.*;

public class Player extends Entity implements IPlayer, CommandSender {

    private final PlayerManager manager;
    private final List<String> permissions;
    public int levels = 0;
    public float exp = 0f;
    private byte nextWindowID = 0; // Used for windows
    private PlayerID id;
    private PlayerInventory inventory;
    private Gamemode gamemode;
    private boolean gamemodeUpdate; //Keep tracks if gamemode have been change without the client getting the info
    private Client connection;
    private PlayerAbilites abilites;
    private String displayName;
    private PlayerFile playerFile;
    private final short uid;

    private List<Long> loadedChunks;

    public Player(final PlayerManager manager, final Client connection, final PlayerID id, final PlayerInventory inventory,
                  final int entityID, final World world, final Location pos, final PlayerAbilites abilites, final Gamemode gamemode) {
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
            Logging.getLogger().error("Could not load/create player data file for: " + getName());
            return;
        }
        this.loadedChunks = Collections.synchronizedList(new ArrayList<Long>());
    }

    public Player(AbstractPlayer player, Gamemode gm) {
        this(player.getServer().getPlayerManager(), player.getClient(), player.getInfo(), new PlayerInventory((byte) 0x00), Entity.generateEntityID(), player.getWorld(), player.getLocation(), player.getAbilities(), gm);
    }

    public short getUID() {
        return this.uid;
    }

    public void teleport(Location location) {
        this.getLocation().setX(location.getX());
        this.getLocation().setY(location.getY());
        this.getLocation().setZ(location.getZ());
        getClient().sendPacket(new PlayerPositionPacket(location));
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

    public boolean isOnline() {
        return true;
    }

    public byte nextWindowID() {
        return (byte) nextWindowID++;
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

    public PlayerAbilites getAbilities() {
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

    public void updateInventory() {
        this.getClient().sendPacket(new InventoryContentPacket(getInventory()));
    }

    public void sendPostionAndLook() {
        this.getClient().sendPacket(new ClientboundPlayerLookPositionPacket(getPosition()));
    }

    public void sendLook() {
        this.getClient().sendPacket(new PlayerLookPacket(getPosition()));
    }

    public void sendPosition() {
        sendPostionAndLook();
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
        LeaveEvent event = new LeaveEvent(this, LeaveEvent.QuitReason.KICKED);
        Marine.getServer().callEvent(event);
        Marine.broadcastMessage(event.getMessage().replace("%plr", getName()));
        getClient().sendPacket(new KickPacket(
                (reason.length() > 0) ? reason : "Kicked"
        ));
        cleanup();
    }

    private void cleanup() {
        playerFile.saveFile();
    }

    public void disconnect() {
        LeaveEvent event = new LeaveEvent(this, LeaveEvent.QuitReason.NORMAL);
        Marine.getServer().callEvent(event);
        Marine.broadcastMessage(event.getMessage().replace("%plr", getName()));
        cleanup();
    }
}