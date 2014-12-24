package org.marinemc.world.entity.meta;

public class HumanMeta extends LivingEntityMeta {

	public HumanMeta(short air, String nameTag, boolean allwaysShowNameTag, float health, byte numArrowsHit) {
		super(4, air, nameTag, allwaysShowNameTag, health, numArrowsHit);
		
		set(10,new MetaObject((byte) 0));
		set(16,new MetaObject((byte) 0));
		set(17,new MetaObject(0F));
		set(18,new MetaObject(0));
		
		
		
	}

}
