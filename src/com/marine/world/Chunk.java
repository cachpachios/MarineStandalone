package com.marineapi.world;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.marineapi.util.Position;

public final class Chunk {
	
	public final static int SECTION_HEIGHT = 16;
	
	public final static int WIDTH = 16; // X size
	public final static int HEIGHT = 256; // Y Size
	public final static int DEPTH = 16; // Z Size
	
	private ChunkPos pos;
	
	private World world;
	
	private Section[] sections;
	
	private BiomeID[] biomes;
	
	//TODO: Entities etc
	
	
	public static final int SIZE = 16;
	
	public Chunk(World w, ChunkPos pos) {
		world = w;
		sections = new Section[HEIGHT/SECTION_HEIGHT];
		biomes = new BiomeID[WIDTH*DEPTH];
		this.pos = pos;
	}
	
	//TODO: Construct chunk using nbt data
	
	public World getWorld() {
		return world;
	}
	
	public BiomeID getBiomeAt(int x, int z) {
		if(x > 16) return BiomeID.UNKNOWNED;
		if(z > 16) return BiomeID.UNKNOWNED;
		return biomes[x + z * WIDTH];
	}
	
	public void setBiomeAt(int x, int z, BiomeID biome) {
		if(x > 16) return;
		if(z > 16) return;
		biomes[x + z * WIDTH] = biome;
		
		// TODO Cast event to update players with chunkdata packet
	}
	
	public ChunkPos getPos() { return pos; };
	
	public Block getBlockAt(int x, int y, int z) {
		
		if(x > 16)
			return null;
		if(z > 16)
			return null;
		if(y > 256)
			return null;	
		
		int sectionID = (int) ((y / 16) + 0.5f); // Y/16 and add 0.5 for it to round correctly
		
		Section s = sections[sectionID];
		
		if(s==null) return Block.getAirBlock(new Position(x,y,z), this);
		
		
		return s.getBlockAt(x, y - ((sectionID-1) * 16), z);
		

		// TODO Cast event to update players
	}
	
	public void setTypeAt(int x, int y, int z, BlockID type) {
		if(x > 16)
			return;
		if(z > 16)
			return;
		if(y > 256)
			return;
		
		Section s = getSectionAt(x,y,z);
		if(s != null)
			s.setTypeAt(x, y, z, type);
		else {
			if(type == BlockID.AIR) return;
			createEmptySection(y).setTypeAt(x, y, z, type);
		}
			
	}
	
	public Section createEmptySection(int y) {
		int sectionID = (int) ((y / 16) + 0.5f); // Y/16 and add 0.5 for it to round correctly
		Section s = new Section(this, sectionID);
		sections[sectionID] = s;
		return sections[sectionID];
	}
	
	private Section getSectionAt(int x, int y, int z) {
		if(x > 16)
			return null;
		if(z > 16)
			return null;
		if(y > 256)
			return null;	
		
		return sections[(int) ((y / 16) + 0.5f)];// get section Y by Y/16 and add 0.5 for it to round correctly
	}
	
	public class Section {
		private ConcurrentHashMap<Integer, BlockID> blockID;
		private ConcurrentHashMap<Integer, Byte> 	blockLighting;
		
		private int y;
		
		private Chunk parent;
		
		public Section(Chunk c, int SectionID) {
			blockID			 = new ConcurrentHashMap<Integer, BlockID>();
			blockLighting 	 = new ConcurrentHashMap<Integer, Byte>();
			parent = c;
			y = SectionID;
		}
		
		public Section(List<Block> blocks, Chunk c, int SectionID) {
			this(c,SectionID);
			for(Block b : blocks) {
				Position p = getLocalPos(b.getBlockPos());
				if(p.getX() > 16) continue;
				if(p.getY() > 16) continue;
				if(p.getZ() > 16) continue;
				setTypeAt(p.x, p.y, p.z, b.getType());
			}
		}	
		
		public int getNumBlocks() {
			return blockID.size();
		}
		
		private Position getWorldPos(int x, int y, int z) { return new Position(x*pos.getX(),y + (((int) ((this.y / 16) + 0.5f)-1) * 16), z*pos.getY()); }
		private Position getLocalPos(int x, int y, int z) { return new Position(x/pos.getX(),y - (((int) ((this.y / 16) + 0.5f)-1) * 16), z/pos.getY()); }
		private Position getLocalPos(Position pos) { return getLocalPos(pos.x, pos.y, pos.z); }
		
		public Block getBlockAt(int x, int y, int z) {
			if(!blockID.containsKey(x * z + y))
				return Block.getAirBlock(getWorldPos(x,y,z), parent);
			else {
				int l = 15;
				if(blockLighting.contains(x*z + y))
					l = blockLighting.get(x*z + y);

				return new Block(getWorldPos(x,y,z), parent, l , blockID.get(x * z + y));
			}
		}
		
		
		public void setTypeAt(int x, int y, int z, BlockID b) {
			
			if(x > 16) return;
			if(y > 16) return;
			if(z > 16) return;
			
			if((b == BlockID.AIR) && !blockID.contains(x*z + y)) return; // If you are trying to set block to Air and its allready air there 
			
			if(!blockID.containsKey(x * z + y))
				blockID.remove(x * z + y);

			if(b != BlockID.AIR)
			blockID.put(x*z + y, b);

			//TODO Calculate posseble new lighting.
			
		}
		
	}	
}
