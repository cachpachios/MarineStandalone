package com.marine.world.entity;

import com.marine.Logging;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.util.Vector3d;
import com.marine.world.World;

public abstract class Entity {

    private static int nextEntityID = 0;
    private final int entityID;
    private final EntityType type;
    private World world;
    private Location position;
    private int ticksLived;

    public Entity(final EntityType type, final int ID, final Location pos) {
        this(type, ID, pos.getWorld(), pos);
    }

    public Entity(final EntityType type, final int ID, final World world, final Location pos) {
        this.entityID = ID;
        this.world = pos.getWorld();
        this.position = pos;
        this.ticksLived = 0;
        this.type = type;
    }

    public static int generateEntityID() {
        return nextEntityID++;
    }

    public EntityType getType() {
        return this.type;
    }

    public abstract int getSendDistance();

    public abstract void update(); // Called each tick for ai/other updating

    public final void tick() {
        if (ticksLived >= Integer.MAX_VALUE - 2) {
            Logging.getLogger().error("Entity lived more than 3.6 years impressive! but now we have to remove some age because of memory:/");
            ticksLived = -1;
        }
        ++ticksLived;
    }

    @Override
    public String toString() {
        return "MarineEntity{\"id\":" + getEntityID() + "}";
    }

    public int getEntityID() {
        return entityID;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }

    public Position getRelativePosition() {
        return position.getRelativePosition();
    }

    public Vector3d getAbsolutePosition() {
        return position;
    }

    public Location getPosition() {
        return position;
    }

    public int getSecoundsLived() {
        return ticksLived / 20;
    }

    public int getTicksLived() {
        return ticksLived;
    }

}
