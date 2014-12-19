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

import org.marinemc.net.play.clientbound.player.SpawnPlayerPacket;
import org.marinemc.net.play.clientbound.world.entities.EntityLookMovePacket;
import org.marinemc.player.Player;
import org.marinemc.util.Location;

/**
 * Movement Manager
 *
 * @author Fozie
 */
public class MovementManager {

    public static final int MAX_PACKET_MOVEMENT = 5;

    private final PlayerManager players;

    public MovementManager(PlayerManager players) {
        this.players = players;
    }

    public void spawnPlayersLocally(final Player target) {
//    	for(final Chunk c : target.getAllLoadedChunks())
//    		for(final Player p : c.getSubscribingPlayers())
//    			if(p.getUID() != target.getUID()) {
//    				target.getClient().sendPacket(new SpawnPlayerPacket(p));
//    				System.out.println("Sent: " + p.getName() + " to " + target.getName());
//    			}
    	
    	//TODO: TEMP CODE:
    	for(Player p : players.getPlayers())
            if (target.getUID() != p.getUID())
                target.getClient().sendPacket(new SpawnPlayerPacket(p));
    }

    public void registerLook(Player p, float yaw, float pitch) {
        p.getLocation().setYaw(yaw);
        p.getLocation().setPitch(pitch);
        
        updatePlayerChunk(p);
        
        // TODO Send to every other players in a sphere of ? blocks
    }
   
    public void updatePlayerChunk(final Player ref) {
//    	for(final Chunk c : ref.getAllLoadedChunks())
//    		for(final Player p : c.getSubscribingPlayers())
//    			if(p.getUID() != ref.getUID())
//    				p.getClient().sendPacket(new EntityLookMovePacket(ref));
    	
    	//TODO: TEMP CODE:
    	for(Player p : players.getPlayers())
            if (ref.getUID() != p.getUID())
                p.getClient().sendPacket(new EntityLookMovePacket(ref));
    }
    
    public void teleport(Player p, Location target) {
        p.sendPositionAndLook();
        updatePlayerChunk(p);
    }

    public void registerMovement(Player p, Location target) {
        if (p == null) {
            return;
        }
        if (target == null) {
            p.sendPositionAndLook();
            return;
        }
        if (checkMovement(p, target)) {
            p.getLocation().setX(target.getX());
            p.getLocation().setY(target.getY());
            p.getLocation().setZ(target.getZ());

            p.getLocation().setYaw(target.getYaw());
            p.getLocation().setPitch(target.getPitch());

            p.getLocation().setOnGround(target.isOnGround());
            updatePlayerChunk(p);
        } else {
            p.sendMessage("You moved too quickly");
            p.sendPositionAndLook();
        }
    }

    public boolean checkMovement(Player p, Location target) { // Current position is p.getLocation();
    	return true;
        //return p.getLocation().getEuclideanDistanceSquared(target) <= MAX_PACKET_MOVEMENT * MAX_PACKET_MOVEMENT;
    }

}
