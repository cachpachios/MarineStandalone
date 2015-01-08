package org.marinemc.world.entity.meta;

public abstract class EntityMeta extends Metadata {

	// TODO: Bitmap withboolean onFire, boolean crouched, boolean sprinting,
	// boolean blocking, boolean invis
	public EntityMeta(final int size, final short air) {
		super(2 + size);

		add(0, new MetaObject((byte) 0)); // TODO: Bitmap with booleans onFire,
											// crouch, sprinting, blocking,
											// invis

		add(1, new MetaObject(air));
	}
}
