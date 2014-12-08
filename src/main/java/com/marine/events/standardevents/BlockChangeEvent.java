package com.marine.events.standardevents;

import com.marine.events.Cancellable;
import com.marine.events.MarineEvent;
import com.marine.util.Position;
import com.marine.world.BlockID;

public class BlockChangeEvent extends MarineEvent implements Cancellable {

	boolean isCancelled;
	
	
	public BlockChangeEvent(Position blockPos, BlockID current, BlockID target) {
		super("BlockChangeEvent");
		this.blockPos = blockPos;
		this.current = current;
		this.target = target;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		isCancelled = true;
	}
	private final Position blockPos;
	private final BlockID current, target;
	
}
