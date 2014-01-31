package com.uprightpath.twentyeight.game;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;

public class Mine {
	public static final Tile ERROR_TILE = new Tile(true, -1, -1, 0);
	private int time = 0;
	private int startX;
	private int startY;
	private int sizeX;
	private int sizeY;
	private Tile[][] map;
	private Array<Explosion> explosionList = new Array<Explosion>();
	private Array<RagBall> ragList = new Array<RagBall>();
	private MineEventDispatcher dispatcher = new MineEventDispatcher();

	public Mine(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		map = new Tile[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				map[i][j] = new Tile(true, i, j, 0);
			}
		}
	}

	public Mine(boolean[][] walls, int[][] gasValues) {
		this(walls.length, walls[0].length);
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				map[i][j].wall = !walls[i][j];
				map[i][j].gas = gasValues[i][j];
			}
		}
	}

	public boolean ignite(int posX, int posY) {
		if (getTile(posX, posY) != ERROR_TILE && getTile(posX, posY).gas >= 5) {
			Explosion explosion = new Explosion(this, map[posX][posY]);
			explosionList.add(explosion);
			return true;
		}
		return false;
	}

	public void update() {
		boolean exploded = false;
		Explosion explosion;
		Iterator<RagBall> ragIterator = ragList.iterator();
		RagBall rag;
		while (ragIterator.hasNext()) {
			rag = ragIterator.next();
			rag.update();
			if (rag.time == 0 || ignite(rag.posX, rag.posY)) {
				ragIterator.remove();
			} else {
			}
		}
		Iterator<Explosion> explosionIterator = explosionList.iterator();
		while (explosionIterator.hasNext()) {
			explosion = explosionIterator.next();
			exploded |= explosion.update();
			if (explosion.isOver()) {
				explosionIterator.remove();
			}
		}
		if (exploded) {
			dispatcher.ignites();
		}
		dispatcher.update();
		time++;
	}

	public Tile getTile(int posX, int posY) {
		return (posX >= 0 && posY >= 0 && posX < sizeX && posY < sizeY) ? map[posX][posY] : ERROR_TILE;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void fling(RagBall ragBall) {
		ragList.add(ragBall);
	}

	public boolean hasWon() {
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (!getTile(i, j).wall && !getTile(i, j).ignited && getTile(i, j).gas > 5) {
					return false;
				}
			}
		}
		return explosionList.size == 0;
	}

	public int getTime() {
		return time;
	}

	public MineEventDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(MineEventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStart(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public float tileCoverage() {
		int count = 0;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (!getTile(i, j).wall) {
					count++;
				}
			}
		}
		return (float) count / (float) (sizeX * sizeY);
	}

	public int gasCoverage() {
		int count = 0;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (!getTile(i, j).wall) {
					count += getTile(i, j).gas;
				}
			}
		}
		return count;
	}
}
