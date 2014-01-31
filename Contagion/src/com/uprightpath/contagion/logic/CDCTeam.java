package com.uprightpath.contagion.logic;

public class CDCTeam {
	public enum State {
		WAITING, WORKING, COOLINGDOWN;
	}

	public World world;
	public State state = State.WAITING;
	public int timeDone;
	private City city;

	public CDCTeam(World world) {
		this.world = world;
	}

	public void setState(State state, City city) {
		if (this.state != state) {
			switch (state) {
			case WAITING: {
				world.teamQueue.add(this);
				break;
			}
			default:
				this.timeDone = world.cureTime;
				if (this.city != null) {
					this.timeDone *= this.city.infectionCoeff;
					this.city.team = null;
				}
				break;
			}
			this.state = state;
			this.city = city;
		}
	}

	public void update() {
		timeDone--;
		if (isDone()) {
			switch (state) {
			case WORKING: {
				setState(CDCTeam.State.COOLINGDOWN, null);
				break;
			}
			case COOLINGDOWN: {
				setState(CDCTeam.State.WAITING, null);
				break;
			}
			default: {
			}
			}
		}
	}

	public boolean isDone() {
		return timeDone < 1;
	}
}
