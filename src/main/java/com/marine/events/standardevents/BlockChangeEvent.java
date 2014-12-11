package com.marine.events.standardevents;

import com.marine.events.Cancellable;
import com.marine.events.MarineEvent;
import com.marine.util.Position;
import com.marine.world.BlockID;

public class BlockChangeEvent extends MarineEvent implements Cancellable {

	boolean isCancelled;

	private final Position blockPos;
	private final BlockID current;
	private BlockID target;
	
	public BlockChangeEvent(Position blockPos, BlockID current, BlockID target) {
		super("BlockChangeEvent");
		this.blockPos = blockPos;
		this.current = current;
		this.target = target;
		this.isCancelled = false;
	}

	public BlockID getPrevious() {
		return this.current;
	}

	public BlockID getNew() {
		return this.target;
	}

	public void setNew(BlockID target) {
		if (target == this.getPrevious())
			this.setCancelled(true);
		else
			this.target = target;
	}

	public Position getPosition() {
		return this.blockPos;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		isCancelled = true;
	}
	
}
