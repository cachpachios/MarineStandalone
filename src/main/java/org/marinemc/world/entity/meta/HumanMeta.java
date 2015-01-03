package org.marinemc.world.entity.meta;

public class HumanMeta extends LivingEntityMeta {

	public HumanMeta(short air, String nameTag, boolean allwaysShowNameTag, float health, byte numArrowsHit) {
		super(4, air, nameTag, allwaysShowNameTag, health, numArrowsHit);
		
		add(10,new MetaObject((byte) 0));
		add(16,new MetaObject((byte) 0));
		add(17,new MetaObject((float)0F));
		add(18,new MetaObject((int)0));
		
		
		
	}

}
