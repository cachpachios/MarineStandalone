package com.marine.util;

import org.json.JSONObject;

import com.marine.world.chunk.ChunkPos;

/**
 * Position class
 */
public class Position extends Vector3i {

    public Position(int x, int y, int z) { // Used for relative posisitions (Blocks etc)
        super(x, y, z);
    }

    public Position() {
        this(0, 0, 0);
    }

    public void decode(long l) {
        setX((int) (l >> 38));
        setY((int) ((l >> 26) & 0xFFF));
        setZ((int) (l << 38 >> 38));
    }

    
    public static Position Decode(long l) {
        int x = ((int) (l >> 38));
        int y =((int) ((l >> 26) & 0xFFF));
        int z =((int) (l << 38 >> 38));
        return new Position(x,y,z);
    }
    
    public long encode() {
        return ((getX() & 0x3FFFFFF) << 38) | ((getY() & 0xFFF) << 26) | (getZ() & 0x3FFFFFF);
    }

    public Location toLocation() {
        return new Location(null, getX(), getY(), getZ(), 0, 0);
    }

    public JSONObject toJSONObject() {
        JSONObject o = new JSONObject();
        try {
            o.put("x", getX());
            o.put("y", getY());
            o.put("z", getZ());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

	public ChunkPos getChunkPos() {
		return new ChunkPos(getX() >> 4, getZ() >> 4);
	}
	
	public Position getChunkBlockPos() {
		return new Position(getX()/16, getY(), getZ()/16);
	}

}
