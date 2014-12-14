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

package org.marinemc.game;

import org.marinemc.player.Player;
import org.marinemc.util.Location;

/**
 * @author Fozie
 */
public class MovementManager { // Used to keep track of player movments and send them to the player
    public static int MAX_PACKET_MOVMENT = 5;
    private final PlayerManager players;

    public MovementManager(PlayerManager players) {
        this.players = players;
    }

    public void spawnPlayersLocaly(final Player target) {

    }

    public void registerLook(Player p, float yaw, float pitch) {
        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);

        // TODO Send to every other players in a sphere of ? blocks

    }

    public void teleport(Player p, Location target) {

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
