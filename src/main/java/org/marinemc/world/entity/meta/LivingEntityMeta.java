package org.marinemc.world.entity.meta;

public class LivingEntityMeta extends EntityMeta{

	public LivingEntityMeta(int size, short air, String nameTag, boolean allwaysShowNameTag, float health, byte numArrowsHit) {
		super(size+7, air);
		set(2,new MetaObject(nameTag));
		set(3,new MetaObject(encodeBoolean(allwaysShowNameTag)));
		set(4,new MetaObject(health));
		set(5,new MetaObject(encodeBoolean(false)));
		set(6,new MetaObject(numArrowsHit));
		
		set(15, new MetaObject((byte)0));
	}
	
	private byte encodeBoolean(boolean b) {
		if(b)
			return 0x01;
		else
			return 0x00;
	}
	
}
