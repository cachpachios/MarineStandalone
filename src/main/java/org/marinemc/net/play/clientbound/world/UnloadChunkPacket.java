/**
 * 
 */
package org.marinemc.net.play.clientbound.world;

import java.io.IOException;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.world.chunk.ChunkPos;

/**
 * 
 * Sent to tell client to unload a chunk.
 * Same as ChunkPacket but without information
 * 
 * @author Fozie
 *
 */
public class UnloadChunkPacket extends Packet{

	final ChunkPos pos;
	
	public UnloadChunkPacket(final ChunkPos c) {
		pos = c;
	}
	
	@Override
	public int getID() {
		return 0x21;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData data = new ByteData();
		
		data.writeInt(pos.getX());
		data.writeInt(pos.getY());
		data.writeBoolean(true);
		data.writeShort((short) 0);
		
		stream.write(getID(), data);
	}

	@Override
	public void readFromBytes(ByteData input) {}

	@Override
	public States getPacketState() {
		return States.INGAME;
	}

}
