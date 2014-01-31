package com.uprightpath.twentyeight.game;

public class Tile {
	public int posX;
	public int posY;
	public int gas;
	public int fire;
	public boolean ignited = false;
	public boolean wall;

	public Tile(boolean wall, int posX, int posY, int gas) {
		this.wall = wall;
		this.posX = posX;
		this.posY = posY;
		this.gas = gas;
	}
}
