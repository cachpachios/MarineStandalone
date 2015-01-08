package org.marinemc.world.entity.meta;

public class LivingEntityMeta extends EntityMeta {

	public LivingEntityMeta(final int size, final short air,
			final String nameTag, final boolean allwaysShowNameTag,
			final float health, final byte numArrowsHit) {
		super(size + 7, air);
		add(2, new MetaObject(nameTag));
		add(3, new MetaObject(encodeBoolean(allwaysShowNameTag)));
		add(4, new MetaObject(health));
		add(5, new MetaObject(encodeBoolean(false)));
		add(6, new MetaObject(numArrowsHit));

		add(15, new MetaObject((byte) 0));
	}

	private byte encodeBoolean(final boolean b) {
		if (b)
			return 0x01;
		else
			return 0x00;
	}

}
