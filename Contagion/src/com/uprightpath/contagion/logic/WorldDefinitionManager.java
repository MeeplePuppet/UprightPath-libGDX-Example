package com.uprightpath.contagion.logic;

import com.badlogic.gdx.utils.Array;

public class WorldDefinitionManager {
	private Array<WorldDefinition> worldDefinitions = new Array<WorldDefinition>();

	public WorldDefinitionManager() {

	}

	public int getNumberOfWorlds() {
		return worldDefinitions.size;
	}

	public WorldDefinition getDefinition(int i) {
		return worldDefinitions.get(i);
	}

	public void addDefinition(WorldDefinition worldDefinition) {
		worldDefinitions.add(worldDefinition);
	}
}
