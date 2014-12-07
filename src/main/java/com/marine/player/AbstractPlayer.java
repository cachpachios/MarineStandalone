package com.marine.player;

import com.marine.StandaloneServer;
import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.World;

import java.util.UUID;

/**
 * Abstract Player Class
 * <p/>
 * Used for communication with the client/login process
 */
public class AbstractPlayer implements IPlayer {

    private final StandaloneServer s;

    private final PlayerID id;
    private final Client client;
    private PlayerAbilites abilities;
    private Location location;

    private World w;

    public AbstractPlayer(StandaloneServer server, World w, PlayerID id, Client c, PlayerAbilites abilites, Location spawnLocation) {
        this.s = server;
        this.w = w;
        this.client = c;
        this.location = spawnLocation;
        this.abilities = abilites;
        this.id = id;
    }

    public String getName() {
        return id.getName();
    }

    @Override
    public PlayerID getInfo() {
        return id;
    }

    public PlayerAbilites getAbilities() {
        return abilities;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public UUID getUUID() {
        return id.getUUID();
    }

    @Override
    public void update() {
        if (abilities.needUpdate())
            client.sendPacket(abilities.getPacket());
    }

    @Override
    public World getWorld() {
        return w;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Position getRelativePosition() {
        return location.getRelativePosition();
    }

    public StandaloneServer getServer() {
        return s;
    }
}