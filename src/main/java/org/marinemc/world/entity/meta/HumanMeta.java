package org.marinemc.world.entity.meta;

public class HumanMeta extends LivingEntityMeta {

	public HumanMeta(final short air, final String nameTag,
			final boolean allwaysShowNameTag, final float health,
			final byte numArrowsHit) {
		super(4, air, nameTag, allwaysShowNameTag, health, numArrowsHit);

		add(10, new MetaObject((byte) 0));
		add(16, new MetaObject((byte) 0));
		add(17, new MetaObject(0F));
		add(18, new MetaObject(0));

	}

}
