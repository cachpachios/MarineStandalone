package com.marine.player;

import com.marine.net.play.clientbound.PlayerAbilitesPacket;

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

	protected boolean needUpdate() {
		return needUpdate;
	}
	
	protected void resetUpdate() {
		needUpdate = false;
	}
	
	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
		this.needUpdate = true;
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
