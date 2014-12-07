package com.marine.world.generators;

import com.marine.world.Dimension;
import com.marine.world.World;
import com.marine.world.chunk.Chunk;

public abstract class WorldGenerator {

    protected World world;

    public WorldGenerator() {
    }

    public void setWorld(World w) {
        world = w;
    }

    public Chunk[] generateRegion(int x, int y) {
        return generateRegion(x, y, 16, 16);
    }

    public abstract LevelType getLevelType();

    public abstract Chunk[] generateRegion(int x, int y, int width, int height);

    public abstract Dimension getDimension(); //TODO Enum for dimensions

    public abstract Chunk generateChunk(int x, int y);
}
