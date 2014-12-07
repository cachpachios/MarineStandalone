package com.marine.player;

import java.util.UUID;

/**
 * Player ID Class
 */
public class PlayerID {
    public final String name;
    public final UUID uuid;

    /**
     * Constructor
     *
     * @param name Username
     * @param uuid Universally Unique Identifier
     */
    public PlayerID(final String name, final UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Get the username
     *
     * @return Username
     */
    public String getName() {
        return name;
    }

    /**
     * Get the universally unique identifier
     *
     * @return universally unique identifier
     */
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int hashCode() {
        return ((37 * name.hashCode()) + (37 * uuid.hashCode())) / 37;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
