package com.uprightpath.twentyeight.game;

public class Player {
	public Mine mine;
	public Direction direction = Direction.DOWN;
	public int posX;
	public int posY;
	public int health;
	public int maxHealth;
	public int maxLost;
	public int rags;
	public int maxRags;

	public Player(int maxHealth, int maxRags) {
		this.maxHealth = maxHealth;
		health = maxHealth;
		this.maxRags = maxRags;
		rags = maxRags;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
		posX = mine.getStartX();
		posY = mine.getStartY();
		maxHealth -= Math.max(0, maxLost - 2);
		health = maxHealth;
		this.rags = maxRags;
	}

	public void update() {
		maxLost = Math.max(maxLost, mine.getTile(posX, posY).fire);
		if (mine.getTile(posX, posY).fire > 2) {
			health -= 1;
			mine.getDispatcher().playerBurned();
		}
	}

	public void turn(Direction direction) {
		if (this.direction != direction) {
			this.direction = direction;
		}
	}

	public boolean move(Direction direction) {
		int x = posX + direction.offsetX, y = posY + direction.offsetY;
		if (!mine.getTile(x, y).wall) {
			this.direction = direction;
			posX = x;
			posY = y;
			return true;
		} else {
			return false;
		}
	}

	public boolean candle() {
		return mine.ignite(posX + direction.offsetX, posY + direction.offsetY);
	}

	public boolean fling() {
		if (rags > 0 && !mine.getTile(posX + direction.offsetX, posY + direction.offsetY).wall) {
			mine.fling(new RagBall(posX, posY, direction));
			mine.getDispatcher().ragFling();
			return true;
		} else {
			return false;
		}
	}
}
