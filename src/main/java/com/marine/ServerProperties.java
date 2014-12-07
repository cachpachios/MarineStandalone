package com.marine;

public class ServerProperties {

    // BUILD INFO
    public static String BUILD_VERSION = "0.0.1-SNAPSHOT";
    public static String BUILD_TYPE = "Development";
    public static String BUILD_NAME = "WorldWideWorld";
    public static boolean BUILD_STABLE = false;

    //FINAL VALUES
    public static final int PROTOCOL_VERSION = 47;
    public static final String MINECRAFT_NAME = "1.8";
    public static final int MAX_Y = 256;

    private static long currentTick;

    protected static void tick() {
        currentTick++;
    }

    public static long getLifeTime() {
        return currentTick;
    }
}
