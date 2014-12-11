package com.marine.game;

import com.marine.player.Player;
import com.marine.util.Location;

public class MovmentManager { // Used to keep track of player movments and send them to the player
    public static int MAX_PACKET_MOVMENT = 5;
    private final PlayerManager players;

    public MovmentManager(PlayerManager players) {
        this.players = players;
    }

    public void registerLook(Player p, float yaw, float pitch) {
        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        // TODO Send to every other players in a sphere of ? blocks

    }

    public void registerMovment(Player p, Location target) {
        boolean allowed = true; //checkMovment(p, target);
        if (allowed) {
            p.getLocation().setX(target.getX());
            p.getLocation().setY(target.getY());
            p.getLocation().setZ(target.getZ());

            p.getLocation().setYaw(target.getYaw());
            p.getLocation().setPitch(target.getPitch());

            p.getLocation().setOnGround(target.isOnGround());

            // TODO Send to every other players in a sphere of ? blocks
        } else {
            p.sendMessage("You moved to quickly :<");
            p.sendPostionAndLook();
        }

    }

    public boolean checkMovment(Player p, Location target) { // Current position is p.getLocation();
        if (p.getLocation().getEuclideanDistance(target) > MAX_PACKET_MOVMENT)
            return false;
        return true; // TODO : Some cheat check in diffrent levels :P
    }

}
