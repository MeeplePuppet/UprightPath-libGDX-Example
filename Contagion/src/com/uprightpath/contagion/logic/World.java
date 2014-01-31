package com.uprightpath.contagion.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class World {
	public final int infectionRate;
	private float eProb;
	private float sumProb = 0;
	public float mutateChance;
	public int cityNum = 0;
	public int numCure;
	public int cureTime;
	public int worldAlive = 0;
	public boolean mutated = false;
	public final Array<CDCTeam> teamQueue = new Array<CDCTeam>();
	public final Array<CDCTeam> teams = new Array<CDCTeam>();
	public final City[] cities;
	public WorldDefinition definition;

	public World(WorldDefinition definition, float mutateChance, int infectionRate, int cureTime, int numCDC) {
		this.definition = definition;
		cities = new City[definition.cities.size];
		this.infectionRate = infectionRate;
		this.cureTime = cureTime;
		this.mutateChance = mutateChance;
		eProb = (float) Math.pow(Math.E, -mutateChance);
		CDCTeam team;
		for (int i = 0; i < numCDC; i++) {
			team = new CDCTeam(this);
			teamQueue.add(team);
			teams.add(team);
		}
	}

	public void addCity(City city) {
		cities[city.id] = city;
	}

	public void linkCity(int first, int second) {
		cities[first].adjacentCities.add(cities[second]);
		cities[second].adjacentCities.add(cities[first]);
	}

	public void assignCDCTeam(City city) {
		if (!mutated && teamQueue.size > 0 && city.team == null) {
			city.assignCDCTeam(teamQueue.removeIndex(0));
			city.team.timeDone = cureTime;
		}
	}

	public void globalCure() {
		if (!mutated) {
			numCure++;
			sumProb += Math.pow(mutateChance, numCure) / fact(numCure);
			if (MathUtils.random() < eProb * sumProb) {
				mutated = true;
			}
			for (City city : cities) {
				if (!mutated) {
					city.infectionLevel = 0;
				}
			}
			for (CDCTeam team : teams) {
				if (team.state == CDCTeam.State.WORKING) {
					team.setState(CDCTeam.State.COOLINGDOWN, null);
				}
			}
		}
	}

	public void update() {
		worldAlive++;
		for (City city : cities) {
			city.computeNextInfectionLevel();
		}
		for (City city : cities) {
			city.applyNextInfectionLevel();
		}
		for (CDCTeam team : teams) {
			team.update();
		}
	}

	public boolean hasLost() {
		boolean lost = true;
		for (City city : cities) {
			lost = city.infectionLevel == 100 && lost;
		}
		return lost;
	}

	private int fact(int i) {
		if (i < 2) {
			return 1;
		} else {
			return i * fact(i - 1);
		}
	}
}
