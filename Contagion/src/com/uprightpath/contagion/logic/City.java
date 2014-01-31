package com.uprightpath.contagion.logic;

import com.badlogic.gdx.utils.Array;

public class City {
	public final World world;
	public final float x;
	public final float y;
	public final int id;
	public Array<City> adjacentCities = new Array<City>();
	public float infectionCoeff;
	public float infectionLevel;
	public CDCTeam team;
	private float nextInfectionLevel;

	public City(World world, int id, float x, float y, float infectionCoeff) {
		this.world = world;
		this.id = id;
		this.x = x;
		this.y = y;
		this.infectionCoeff = infectionCoeff;
	}

	public void assignCDCTeam(CDCTeam team) {
		this.team = team;
		team.setState(CDCTeam.State.WORKING, this);
		infectionLevel = 0;
		infectionCoeff *= 1.25;
	}

	public boolean isSpreadingInfection() {
		return infectionLevel == 100 && team == null;
	}

	public void computeNextInfectionLevel() {
		float totalInfectionCoeff = infectionCoeff;
		for (int i = 0; i < adjacentCities.size; i++) {
			if (adjacentCities.get(i).isSpreadingInfection()) {
				totalInfectionCoeff += adjacentCities.get(i).infectionCoeff;
			}
		}
		nextInfectionLevel = Math.min(infectionLevel + totalInfectionCoeff * world.infectionRate, 100);
	}

	public void applyNextInfectionLevel() {
		if (team == null) {
			infectionLevel = nextInfectionLevel;
		}
	}
}
