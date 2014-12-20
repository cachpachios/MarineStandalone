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

package org.marinemc.net.handshake;

import org.json.simple.JSONArray;

/**
 * Created 2014-12-02 for MarineStandalone
 *
 * @author Citymonstret
 */
public class ListResponse {

    private String motd, favicon;
    private int maxPlayers, currentPlayers;
    private JSONArray samplePlayers;

    /**
     * Create a new list response
     *
     * @param motd           Message of the day
     * @param currentPlayers Current player count
     * @param maxPlayers     Max player count
     * @param samplePlayers  Sample player list
     * @param favicon        Server icon
     */
    public ListResponse(String motd, int currentPlayers, int maxPlayers, JSONArray samplePlayers, String favicon) {
        this.motd = motd;
        this.favicon = favicon;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.samplePlayers = samplePlayers;
    }

    public String getMOTD() {
        return this.motd;
    }

    public String getFavicon() {
        return this.favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getCurrentPlayers() {
        return this.currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public JSONArray getSamplePlayers() {
        return this.samplePlayers;
    }

    public void setSamplePlayers(JSONArray samplePlayers) {
        this.samplePlayers = samplePlayers;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }
}
