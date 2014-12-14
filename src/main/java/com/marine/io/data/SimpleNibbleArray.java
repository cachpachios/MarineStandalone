package com.marine.io.data;

import com.marine.util.Hacky;

import java.util.ArrayList;
import java.util.List;


/**
 * Hacky and memory wasting implementation of NibbleArray, sometimes preformance saving until toBytes() is called.
 * Anyway not recommended at all!
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
			if (comp == null) {
				comp = (byte) ((b & 0xF) << 4);
			}
			else {
				comp = (byte) (comp | (b & 0xF));
				r.add(comp);
				comp = null;
			}
		return ByteData.unwrap((Byte[]) r.toArray());
	}

	@Override
	public boolean contains(final byte nibble) {
		return super.contains(nibble);
	}	
}