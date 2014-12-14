package com.marine.io.data;

import java.util.ArrayList;
import java.util.List;

import com.marine.util.annotations.Hacky;


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
		List<Byte> r = new ArrayList<Byte>();
		
		Byte comp = null;
		for(byte b : this)
			if(comp==null) {
				comp = (byte) ((b &0xF)<<4);
			}
			else {
				comp = (byte) (comp | (b&0xF));
				r.add(comp);
				comp = null;
			}
		
		return ByteData.unwrap((Byte[]) r.toArray());
	}

	@Override
	public boolean contains(byte nibble) {
		return ((ArrayList<Byte>)(this)).contains(nibble);
	}	
}