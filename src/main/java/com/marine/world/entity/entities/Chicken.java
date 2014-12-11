package com.marine.world.entity.entities;

import com.marine.util.Location;
import com.marine.world.entity.Entity;
import com.marine.world.entity.EntityType;

/**
 * Created 2014-12-11 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Chicken extends Entity {

    public Chicken(int ID, Location pos) {
        super(EntityType.CHICKEN, ID, pos);
    }

    @Override
    public int getSendDistance() {
        return 0;
    }

    @Override
    public void update() {

    }
}
