package com.marine.world;

import com.marine.util.Position;
import com.marine.world.chunk.Chunk;

public class Block { // Help class for easier reading not used to save/set data

    private Position blockPos;
    private Chunk chunk;
    private int lighting;
    private BlockID type;

    public Block(Position blockPos, Chunk chunk, int lighting, BlockID type) {
        this.blockPos = blockPos;
        this.chunk = chunk;
        this.lighting = lighting;
        this.type = type;
    }

    public static Block getAirBlock(Position pos, Chunk chunk) {
        return new Block(pos, chunk, 0, BlockID.AIR);
    }

    public Position getBlockPos() {
        return blockPos;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public int getLighting() {
        return lighting;
    }

    public BlockID getType() {
        return type;
    }

    public int getNBTBlockPos() {
        return blockPos.getY() * 16 * 16 + blockPos.getZ() * 16 + blockPos.getX();
    }

    public int toPacketBlock() {
        return type.getID() << 4; // TODO Replace 0 with avalible metadata
    }

}

