package com.marine.player;

import java.util.UUID;

import com.marine.game.chat.RawChatMessage;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.World;
import com.marine.world.entity.Entity;

public class Player extends Entity implements IPlayer, CommandSender {
	
	private PlayerID id;
	
	private Client connection;
	
	private PlayerAbilites abilites;
	
	public Player(Client connection, PlayerID id, int entityID, World world, Location pos, PlayerAbilites abilites) {
		super(entityID, world, pos);
		this.id = id;
		this.abilites = abilites;
		this.connection = connection;
	}
	
	public Player(AbstractPlayer player) {
		this(player.getClient(),player.getInfo(), Entity.generateEntityID(), player.getWorld(), player.getLocation(), player.getAbilites());
	}

	@Override
	public String getName() {
		return	id.getName();
	}
	
	@Override
	public int getSendDistance() { // TODO Make this more dynamic and ability to use with client render distance
		return 200;
	}

	@Override
	public void update() {
		if(abilites.needUpdate())
			connection.sendPacket(abilites.getPacket());
	}

	@Override
	public PlayerID getInfo() {
		return id;
	}

	@Override
	public Client getClient() {
		// TODO Auto-generated method stub
		return connection;
	}

	@Override
	public UUID getUUID() {
		return id.getUUID();
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

	@Override
	public Location getLocation() {
		return this.getLocation();
	}

	@Override
	public Position getRealtivePosition() {
		return this.getRealtivePosition();
	}
}
