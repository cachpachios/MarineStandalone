package com.marine.player;

import com.marine.game.chat.RawChatMessage;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.world.World;
import com.marine.world.entity.Entity;

import java.util.UUID;

public class Player extends Entity implements IPlayer, CommandSender {
	
	private AbstractPlayer absPlayer;
	
	public Player(Client connection, PlayerID id, int entityID, World world, Location pos) {
		super(entityID, world, pos);
		this.absPlayer = new AbstractPlayer(id, connection);
	}
	
	@Override
	public String getName() {
		return absPlayer.getName();
	}
	
	@Override
	public int getSendDistance() { // TODO Make this more dynamic and abilites to use with client render distance
		return 200;
	}

	@Override
	public void update() {
	}

	@Override
	public PlayerID getInfo() {
		return absPlayer.getInfo();
	}

	@Override
	public Client getClient() {
		// TODO Auto-generated method stub
		return absPlayer.getClient();
	}

	@Override
	public UUID getUUID() {
		return absPlayer.getUUID();
	}

    @Override
    public void executeCommand(String command) {

    }

    @Override
    public void executeCommand(String command, String[] arguments) {

    }

    @Override
    public void executeCommand(Command command, String[] arguments) {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(RawChatMessage message) {

    }
}
