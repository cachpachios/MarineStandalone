package org.marinemc.util.vectors;


/*
 * @author Fozie
 */
public final class Vector3b extends Vector3<Byte> {

	public Vector3b(Byte x, Byte y, Byte z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(Vector3<Byte> v2) {
		this.x = (byte) (x.byteValue() + v2.x.byteValue());
		this.y = (byte) (y.byteValue() + v2.y.byteValue());
		this.z = (byte) (z.byteValue() + v2.z.byteValue());
	}

	@Override
	public void subtract(Vector3<Byte> v2) {
		this.x = (byte) (x.byteValue() - v2.x.byteValue());
		this.y = (byte) (y.byteValue() - v2.y.byteValue());
		this.z = (byte) (z.byteValue() - v2.z.byteValue());
	}

	@Override
	public void multiply(int n) {
		this.x = (byte) (x.byteValue() * n);
		this.y = (byte) (y.byteValue() * n);
		this.z = (byte) (z.byteValue() * n);
	}

	@Override
	public void divide(int n) {
		this.x = (byte) (x.byteValue() / n);
		this.y = (byte) (y.byteValue() / n);
		this.z = (byte) (z.byteValue() / n);
	}

}
