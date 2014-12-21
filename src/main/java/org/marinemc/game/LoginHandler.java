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

import org.marinemc.events.EventManager;
import org.marinemc.events.standardevents.JoinEvent;
import org.marinemc.events.standardevents.PreLoginEvent;
import org.marinemc.game.async.ChatManager;
import org.marinemc.net.Client;
import org.marinemc.net.States;
import org.marinemc.net.login.LoginSucessPacket;
import org.marinemc.player.*;
import org.marinemc.server.Marine;
import org.marinemc.util.UIDGenerator;
import org.marinemc.util.mojang.UUIDHandler;
import org.marinemc.util.wrapper.StringWrapper;
import org.marinemc.world.World;

import java.util.UUID;

/**
 * @author Fozie
 */
public class LoginHandler {

    private final PlayerManager playerManager;

    public LoginHandler(PlayerManager playerManager, World w) {
        // this.spawnLocation = new Location(w, 0, 5, 0);

        this.playerManager = playerManager;
    }

    private LoginResponse preJoin(String preName, Client c) { // Returns null if login succeded, otherwise makes LoginInterceptor drop the client
        UUID uuid;
        String name;
        if (Marine.getServer().isOfflineMode()) {
            uuid = UUIDHandler.getUuidOfflineMode(new StringWrapper(preName));
            name = preName;
        } else {
            uuid = UUIDHandler.getUUID(preName);
            name = UUIDHandler.getName(uuid);
        }

        if (uuid == null) {
            throw new RuntimeException("UUID == null == BAD!");
        }

        if (playerManager.isPlayerOnline(name))
            return new LoginResponse("Failed to login player is already connected.");
        if (playerManager.isPlayerOnline(uuid))
            return new LoginResponse("Failed to login player is already connected.");

        // Check for uuid ban
        if (Marine.isBanned(uuid))
            return new LoginResponse("Banned from the server");
        
        // Check for ip ban
        if (Marine.isBanned(c.getAdress()))
            return new LoginResponse("IP Banned from the server");

        if (Marine.getPlayers().size() >= Marine.getMaxPlayers())
            return new LoginResponse("Server is full");

        IPlayer p = new AbstractPlayer(playerManager.getServer(), playerManager.getServer().getWorldManager().getMainWorld(), new PlayerID(name, uuid), c, new PlayerAbilities(false, true, false, 0.2f, 0.2f), playerManager.getServer().getWorldManager().getMainWorld().getSpawnPoint().toLocation());

        PreLoginEvent event = new PreLoginEvent(p);
        EventManager.getInstance().handle(event);
        if (!event.isAllowed()) {
            return new LoginResponse(event.getMessage());
        }

        p.getLocation().setWorld(p.getWorld());
        
        short uid = UIDGenerator.instance().getUID(name);
        
        c.setUID(uid);
        
        if (uid == Short.MIN_VALUE) {
            new RuntimeException(
                    String.format("UID Error: (%d) < (%d) for '%s'", uid, Short.MIN_VALUE+1, name)
            ).printStackTrace();
            return new LoginResponse("Failed to log you in. (UID < accepted)");
        }
        
        return new LoginResponse(p);
    }

    public void passPlayer(IPlayer player) { //TODO: Encryption
        Player p = playerManager.passFromLogin(player);

        p.getClient().setUID(p.getUID());
        
        p.getClient().sendPacket(new LoginSucessPacket(p));

        p.getClient().setState(States.INGAME);

        JoinEvent event = new JoinEvent(p, ChatManager.JOIN_MESSAGE);
        Marine.getServer().callEvent(event);
        playerManager.joinGame(p);
        playerManager.getChat().sendJoinMessage(p, event.getJoinMessage());

        //p.getClient().sendPacket(new PlayerListHeaderPacket("&cWelcome to the server", "&6" + player.getName())); //TODO: Custom msg and event :D and togglable

        TablistManager.getInstance().setHeaderAndFooter("&cWelcome to the server", "&6" + player.getName(), p);
        TablistManager.getInstance().addItem(p);
        TablistManager.getInstance().joinList(p);
    }

    public LoginResponse login(String name, Client c) {
        return preJoin(name, c);
    }

    public class LoginResponse {
        public final IPlayer player;
        public final String response;

        public LoginResponse(final IPlayer p) {
            this.player = p;
            this.response = null;
        }

        public LoginResponse(final String response) {
            this.player = null;
            this.response = response;
        }

        public boolean succeed() {
            return player != null;
        }

    }

}

