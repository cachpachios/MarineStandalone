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

package com.marine.util.mojang;

import org.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created 2014-12-04 for MarineStandalone
 *
 * @author Citymonstret
 */
public class MojangUtils {

    private static MojangUtils instance;

    public MojangUtils() {
    }

    public static MojangUtils getInstance() {
        if (instance == null) instance = new MojangUtils();
        return instance;
    }

    private URLConnection getConnection(URL url) throws Throwable {
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        return connection;
    }

    private URL getAuthenticationURL(String username, String serverHash) throws MalformedURLException {
        return new URL(String.format("https://sessionserver.mojang.com/session/minecraft/hasJoined?username=%s&serverId=%s", username, serverHash));
    }

    public org.json.JSONObject hasJoined(final String username, final String serverHash) throws Throwable {
        final URLConnection connection = getConnection(getAuthenticationURL(username, serverHash));
        final JSONTokener tokener = new JSONTokener(connection.getInputStream());
        return new org.json.JSONObject(tokener);
    }

    public Status getStatus(MinecraftService service) {
        String status = null;
        try {
            URL url = new URL("http://status.mojang.com/check?service=" + service.getURL());
            BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
            Object object = new JSONParser().parse(input);
            JSONObject jsonObject = (JSONObject) object;
            status = (String) jsonObject.get(service.getURL());
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Status.UNKNOWN;
        }
        return status(status);
    }

    private Status status(String status) {
        switch (status.toLowerCase()) {
            case "green":
                return Status.ONLINE;

            case "yellow":
                return Status.ISSUES;

            case "red":
                return Status.OFFLINE;

            default:
                return Status.UNKNOWN;
        }
    }

    public static enum Status {
        ONLINE("Service is online"),
        ISSUES("There might be some issues"),
        OFFLINE("Service is offline"),
        UNKNOWN("Could not connect to service");

        private final String desc;

        Status(final String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return this.desc;
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public int getIntegerStatus() {
            switch (this) {
                case ONLINE:
                    return 1;
                case ISSUES:
                    return 2;
                case OFFLINE:
                    return 0;
                case UNKNOWN:
                    return -1;
                default:
                    return -1;
            }
        }
    }

    public static enum MinecraftService {
        WEB("minecraft.net", "Web"),
        LOGIN("login.minecraft.net", "Login"),
        SKIN("skins.minecraft.net", "Skin"),
        ACCOUNT("account.mojang.com", "Account"),
        MOJANG_SESSION("sessionserver.mojang.com", "Mojang Sessions"),
        AUTHSERVER("authserver.mojang.com", "Mojang Authentication"),
        AUTH("auth.mojang.com", "Authentication"),
        MINECRAFT_SESSION("session.minecraft.net", "Minecraft Session");

        private final String url, name;

        MinecraftService(final String url, final String name) {
            this.url = url;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getURL() {
            return this.url;
        }
    }

}
