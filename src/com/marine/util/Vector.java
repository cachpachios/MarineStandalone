/*
 * This file is licensed for the MarineAPI.
 * 
 *  You are allowed to contribute, distribute or in any way modify the code under following conditions:
 *  - NonCommercial, you are not allowed to use this material for commercial purposes, this is to not interfere with Mojang or any contributer.
 *  - If you publish or distribute the code, you have to share the code under the same license and conditions.
 *  - When contributing you are giving the rights of your code to the project not to you self, this is prevent one individual to crash the entire project.
 */

package com.marineapi.util;

public class Vector {
	public int x,y,z;
	
	public Vector(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector() {
		this(0,0,0);
	}
	
	public Vector(int t) {
		this(t,t,t);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
}
