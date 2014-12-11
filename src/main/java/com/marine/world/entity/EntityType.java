package com.marine.world.entity;

import com.marine.util.Vector3d;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public enum EntityType {
    ////////////////////////////////////////////////////////////////////////////
    // For a list of all entities, and the data that should be added for each
    // see: http://wiki.vg/Entities
    ////////////////////////////////////////////////////////////////////////////
    PLAYER(Type.ENTITY, -1, -1d, -1d, -1d), /* Normal rules doesn't apply to players */
    CHICKEN(Type.ENTITY, 93, 0.3, 0.7, 0.3)
    ;

    private final Type type; // (Entity) / (Vehicle / Object)
    private final int id; // entity type ID
    private final Vector3d size; // advanced hit boxes and such

    private EntityType(final Type type, final int id, final double xSize,
                       final double ySize, final double zSize) {
        this.type = type;
        this.id = id;
        this.size = new Vector3d(xSize, ySize, zSize);
    }

    public Vector3d getSize() {
        return this.size;
    }

    public int getID() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public static enum Type {
        ENTITY, OBJECT
    }
}
