package org.marinemc.world.entity;

import org.marinemc.util.Location;
import org.marinemc.util.annotations.Serverside;

public abstract class LivingEntity extends Entity {

	/**
	 * Extending constructor
	 * 
	 * @see Entity
	 * @param type
	 *            The target entity type
	 * @param ID
	 *            The Entity ID
	 * @param pos
	 *            The location of the Entity (Needs to include a world)
	 */
	public LivingEntity(final EntityType type, final int ID, final Location pos) {
		super(type, ID, pos);
	}

	public String displayName;

	public boolean hasDisplayName() {
		return displayName != null;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name warning does not update this information on clients
	 * without reinit
	 * 
	 * @param name
	 *            The target name.
	 */
	@Serverside
	public void setDisplayName(final String name) {
		displayName = name;
	}

}
