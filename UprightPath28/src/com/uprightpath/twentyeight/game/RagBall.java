package com.uprightpath.twentyeight.game;

public class RagBall {
	public int posX;
	public int posY;
	public Direction direction;
	public int time = 4;
	
	public RagBall(int posX, int posY, Direction direction) {
		this.posX = posX;
		this.posY = posY;
		this.direction = direction;
	}
	
	public void update() {
		time--;
		posX += direction.offsetX;
		posY += direction.offsetY;
	}
}
