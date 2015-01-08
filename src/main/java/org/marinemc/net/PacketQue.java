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

package org.marinemc.net;

import java.lang.ref.WeakReference;

/**
 * Created 2014-12-24 for MarineStandalone
 *
 * @author Citymonstret
 */
public class PacketQue {

	private final Multimap<Integer, Packet> que;
	private final WeakReference<Client> c;

	public PacketQue(final Client c) {
		que = ArrayListMultimap.create();
		this.c = new WeakReference<>(c);
	}

	public synchronized void add(final Packet packet) {
		add(packet, 3);
	}

	public synchronized void add(final Packet packet, final int priority) {
		que.put(priority, packet);
	}

	public synchronized void executePackets() {
		if (c.get() == null)
			return;

		for (final int key : que.keys())
			c.get().sendPackets(que.get(key));
	}
}
