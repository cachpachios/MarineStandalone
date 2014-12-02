package com.marine.player;

import java.util.UUID;

import com.marine.game.chat.ChatComponent;
import com.marine.game.chat.ChatMessage;
import com.marine.game.command.Command;
import com.marine.game.command.CommandSender;
import com.marine.net.Client;
import com.marine.util.Location;
import com.marine.util.Position;
import com.marine.world.World;
import com.marine.world.entity.Entity;

public class Player extends Entity implements IPlayer, CommandSender {
	
	private PlayerID id;
	
	private Gamemode gamemode; 
		private boolean gamemodeUpdate; //Keep tracks if gamemode have been change without the client getting the info
	
	private Client connection;
	
	private PlayerAbilites abilites;
	
	public Player(Client connection, PlayerID id, int entityID, World world, Location pos, PlayerAbilites abilites, Gamemode gamemode) {
		super(entityID, world, pos);
		this.id = id;
		this.abilites = abilites;
		this.connection = connection;
		this.gamemode = gamemode;
		this.gamemodeUpdate = false;
	}

	public Player(AbstractPlayer player, Gamemode gm) {
		this(player.getClient(),player.getInfo(), Entity.generateEntityID(), player.getWorld(), player.getLocation(), player.getAbilites(), gm);
	}

	@Override
	public String getName() {
		return	id.getName();
	}
	
	@Override
	public int getSendDistance() { // TODO Make this more dynamic and ability to use with client render distance
		return 200;
	}

	public Gamemode getGamemode() {
		return gamemode;
	}
	
	public void setGamemode(Gamemode gm) {
		this.gamemode = gm;
		this.gamemodeUpdate = true;
	}
	
	@Override
	public void update() {
		if(abilites.needUpdate())
			connection.sendPacket(abilites.getPacket());
		
		if(gamemodeUpdate) this.gamemodeUpdate = false; //TODO Send gamemode packet :)
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


    public boolean isOnline() {
        return true;
    }

	@Override
	public Location getLocation() {
		return this.getLocation();
	}

	@Override
	public Position getRealtivePosition() {
		return this.getRealtivePosition();
	}

	@Override
	public void sendMessage(ChatMessage message) {

	}

	@Override
	public void sendMessage(ChatComponent message) {

	}
}
