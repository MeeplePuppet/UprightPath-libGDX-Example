package com.uprightpath.twentyeight.game;

public enum Direction {
	UP(0, 1, 180), DOWN(0, -1, 0), LEFT(-1, 0, 90), RIGHT(1, 0, -90);
	public final int offsetX;
	public final int offsetY;
	public final int degree;

	Direction(int offsetX, int offsetY, int degree) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.degree = degree;
	}

	public static Direction get(int random) {
		return Direction.values()[random];
	}
}