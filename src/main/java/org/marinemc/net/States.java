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

package org.marinemc.net;
/**
 * Diffrent states the client are in.
 * 
 * @author Fozie
 */
public enum States {
    HANDSHAKE,
    INTRODUCE,
    LOGIN,
    INGAME,
    GLOBAL;

    public static final States getFromID(int id) {
        switch (id) {
            case 0:
                return HANDSHAKE;
            case 1:
                return INTRODUCE;
            case 2:
                return LOGIN;
            case 3:
                return INGAME;
        }
        return HANDSHAKE;

    }

    public int getID() {
        return this.ordinal();
    }
}

	