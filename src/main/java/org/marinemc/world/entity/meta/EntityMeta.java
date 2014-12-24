package org.marinemc.world.entity.meta;


public abstract class EntityMeta extends Metadata {

	//TODO: Bitmap withboolean onFire, boolean crouched, boolean sprinting, boolean blocking, boolean invis
	public EntityMeta(int size, short air) {
		super(2 + size);
		
		add(new MetaObject((byte) 0)); //TODO: Bitmap with booleans onFire, crouched, sprinting, blocking, invis
		
		add(new MetaObject(air));
	}
}
