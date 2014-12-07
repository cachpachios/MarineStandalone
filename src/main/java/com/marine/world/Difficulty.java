package com.marine.world;

public enum Difficulty {
    PEACEFUL("Peaceful", 0),
    EASY("Easy", 1),
    NORMAL("Normal", 2),
    HARD("Hard", 3);

    final String name;
    final byte id;

    private Difficulty(String name, int id) {
        this.name = name;
        this.id = (byte) id;
    }

    public byte getID() {
        return id;
    }
}
