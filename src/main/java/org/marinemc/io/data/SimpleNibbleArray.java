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

package org.marinemc.io.data;

import java.util.ArrayList;
import java.util.List;

import org.marinemc.io.binary.ByteUtils;
import org.marinemc.util.annotations.Hacky;

/**
 * Hacky and memory wasting implementation of NibbleArray, but preformance
 * saving until toBytes() is called. Anyway not recommended at all!
 *
 * @author Fozie
 */
@Hacky
public class SimpleNibbleArray extends ArrayList<Byte> implements NibbleArray {

	private static final long serialVersionUID = -5838149111965617886L;

	@Hacky
	public SimpleNibbleArray() {
		super();
	}

	@Hacky
	@Override
	public byte[] toBytes() {
		final List<Byte> r = new ArrayList<>();
		Byte comp = null;
		for (final byte b : this)
			if (comp == null)
				comp = (byte) ((b & 0xF) << 4);
			else {
				comp = (byte) (comp | b & 0xF);
				r.add(comp);
				comp = null;
			}
		return ByteUtils.unwrap((Byte[]) r.toArray());
	}

	@Override
	public boolean contains(final byte nibble) {
		return super.contains(nibble);
	}
}