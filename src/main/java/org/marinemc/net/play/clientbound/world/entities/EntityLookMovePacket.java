///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.net.play.clientbound.world.entities;

import java.io.IOException;
import java.lang.ref.WeakReference;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.vectors.Vector3;
import org.marinemc.util.vectors.Vector3d;
import org.marinemc.world.entity.Entity;
import org.marinemc.world.entity.EntityTracker;

/**
 * @author Fozie
 */
public class EntityLookMovePacket extends Packet {
	
	final int id;
	final WeakReference<Entity> e;
	final Vector3d last;
	final Vector3d target;
	final WeakReference<EntityTracker> tracker;
	
	
	public EntityLookMovePacket(EntityTracker reciver, Entity ent, Vector3d tracker, Vector3d target) {
		super(0x17, States.INGAME);
		this.id = ent.getEntityID();
		this.e = new WeakReference<>(ent);
		this.last = tracker;
		this.target = target;
		this.tracker = new WeakReference<>(reciver);
	}

	private void error()  {
		if(tracker.get() == null)
			return;
		else
			tracker.get().killLocalEntity(id);
	}
	
	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		if(e.get() == null) 		error();
			
		ByteList data = new ByteList();
		
		data.writeVarInt(e.get().getEntityID());
		
		final Vector3<Byte> sub = last.getDifferientialFixed32(target);
		
		data.writeByte(sub.x);
		data.writeByte(sub.y);
		data.writeByte(sub.z);

		data.writeByte((byte) (((e.get().getLocation().getYaw() % 360) / 360) * 256));
		data.writeByte((byte) (((e.get().getLocation().getPitch() % 360) / 360) * 256));
		
		data.writeBoolean(e.get().getLocation().isOnGround());
		
		stream.write(getID(), data);
	}

}
