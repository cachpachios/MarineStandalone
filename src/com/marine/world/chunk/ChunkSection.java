package com.marine.world.chunk;

import com.marine.io.data.ByteData;
import com.marine.world.BlockID;

public class ChunkSection {
	private int sectionID;
	
	private byte[][][] blockMap;
	
	public ChunkSection(int y) {
		this.sectionID = y;
		this.blockMap = new byte[16][16][16];
	}
	
	public ByteData getData(boolean skyLight) {
		ByteData data = new ByteData();
		int i = 0;
		for(int y = 0; y < 16; y++)
			for(int z = 0; z < 16; z++)
				for(int x = 0; x < 16; x++) {
					data.writeend(blockMap[x][y][z]);
					data.writeend((byte) 0);
					if(skyLight)
						data.writeend((byte)-1);
					else
						if((i & 2) == 0)
							data.writeend((byte)-1);
					i++;
				}
		
		
		
		return data;
	}
	
	public void setBlock(int x, int y, int z, BlockID id) {
		if(x > 16 || x < 0) return;
		if(y > 16 || y < 0) return;
		if(z > 16 || z < 0) return;
		
		blockMap[x][y][z] = id.getID();
	}
	
	public byte getBlockID(int x, int y, int z) {
		if(x > 16 || x < 0) return 0;
		if(y > 16 || y < 0) return 0;
		if(z > 16 || z < 0) return 0;
		
		
		return blockMap[x][y][z];
	}
	
	public BlockID getBlock(int x, int y, int z) {
		return BlockID.AIR;
	}
	
	public int getID() {
		return sectionID;
	}
	
}