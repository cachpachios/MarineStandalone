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

package com.marine.player;

import com.marine.net.play.clientbound.player.PlayerAbilitesPacket;

/**
 * Player abilities
 */
public class PlayerAbilites {
    private boolean godMode, canFly, creativeMode;

    private float flySpeed, walkSpeed;

    private boolean needUpdate; // Used to keep track if a PlayerAbilityPacket needs to be sent to client.

    public PlayerAbilites(boolean godMode, boolean canFly, boolean creativeMode, float flySpeed, float walkSpeed) {
        this.godMode = godMode;
        this.canFly = canFly;
        this.creativeMode = creativeMode;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    public PlayerAbilitesPacket getPacket() {
        return new PlayerAbilitesPacket(this);
    }

    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
        this.needUpdate = true;
    }

    protected boolean needUpdate() {
        return needUpdate;
    }

    protected void resetUpdate() {
        needUpdate = false;
    }

    public boolean canFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
        this.needUpdate = true;
    }

    public boolean isInCreativeMode() {
        return creativeMode;
    }

    public void setCreativeMode(boolean creativeMode) {
        this.creativeMode = creativeMode;
        this.needUpdate = true;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
        this.needUpdate = true;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
        this.needUpdate = true;
    }

    public boolean isFlying() {
        return true; // Temp :p
    }

}
