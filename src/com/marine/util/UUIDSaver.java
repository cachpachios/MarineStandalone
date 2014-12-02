package com.marine.util;

import java.util.UUID;

/**
 * @author Citymonstret
 */
public interface UUIDSaver {
    /**
     * Fetch uuid from mojang servers
     *
     * @param name Username
     * @return uuid
     * @throws Exception
     */
    public UUID mojangUUID(final String name) throws Exception;
    /**
     * Fetch username from mojang servers
     *
     * @param uuid UUID
     * @return username
     * @throws Exception
     */
    public String mojangName(final UUID uuid) throws Exception;
}