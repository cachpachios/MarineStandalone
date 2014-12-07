package com.marine.player;

import com.marine.net.play.clientbound.PlayerAbilitiesPacket;

/**
 * Player abilities
 */
public class PlayerAbilities {

    private boolean godMode, canFly, creativeMode;

    private float flySpeed, walkSpeed;

    private boolean needUpdate; // Used to keep track if a PlayerAbilityPacket needs to be sent to client.

    public PlayerAbilities(boolean godMode, boolean canFly, boolean creativeMode, float flySpeed, float walkSpeed) {
        this.godMode = godMode;
        this.canFly = canFly;
        this.creativeMode = creativeMode;
        this.flySpeed = flySpeed;
        this.walkSpeed = walkSpeed;
    }

    public PlayerAbilitiesPacket getPacket() {
        return new PlayerAbilitiesPacket(this);
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

}
