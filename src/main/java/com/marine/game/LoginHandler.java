///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) IntellectualSites (marine.intellectualsites.com)
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

package com.marine.game;

import com.marine.net.Client;
import com.marine.net.States;
import com.marine.net.login.LoginSucessPacket;
import com.marine.player.*;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.util.UUIDHandler;
import com.marine.world.World;

import java.util.UUID;

public class LoginHandler {

    private final PlayerManager playerManager;

    private Location spawnLocation;

    public LoginHandler(PlayerManager playerManager, World w, Position spawnLocation) {
        this.spawnLocation = new Location(w, 0, 5, 0);

        this.playerManager = playerManager;
    }

    private LoginResponse preJoin(String preName, Client c) { // Returns null if login succeded, otherwise makes LoginInterceptor drop the client
        UUID uuid = UUIDHandler.getUUID(preName); //UUID.randomUUID();
        String name = UUIDHandler.getName(uuid);


        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        if (playerManager.isPlayerOnline(name))
            return new LoginResponse("Failed to login player is already connected.");
        if (playerManager.isPlayerOnline(uuid))
            return new LoginResponse("Failed to login player is already connected.");

        //TODO: Check if player is banned in that case drop them.

        IPlayer p = new AbstractPlayer(playerManager.getServer(), playerManager.getServer().getWorldManager().getMainWorld(), new PlayerID(name, uuid), c, new PlayerAbilites(false, true, false, 0.2f, 0.2f), spawnLocation);

        // c.setUserName(name);
        c.setUID(playerManager.getNextUID());

        return new LoginResponse(p);
    }

    public void passPlayer(IPlayer player) { //TODO: Encryption
        Player p = playerManager.passFromLogin(player);

        p.getClient().sendPacket(new LoginSucessPacket(p));

        p.getClient().setState(States.INGAME);

        playerManager.joinGame(p);

        playerManager.getChat().sendJoinMessage(p);
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

