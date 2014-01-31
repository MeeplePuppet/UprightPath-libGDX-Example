package com.uprightpath.twentyeight.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class MineGenerator {
	private ValuePair<Integer, Integer>[] randomSize;
	private int total;
	private Mine mine;
	private Array<Tile> openTiles = new Array<Tile>();
	private Tile genTile;
	private Direction genDirection;

	public MineGenerator() {
		randomSize = new ValuePair[10];
		for (int i = 0; i < 10; i++) {
			randomSize[i] = new ValuePair<Integer, Integer>((10 - i) * 10, i * 2 + 10);
			total += (10 - i) * 10;
		}
	}

	public int getRandomSize() {
		int x = MathUtils.random(total - 1);
		for (int i = 0; i < randomSize.length; i++) {
			if (x > randomSize[i].a) {
				x -= randomSize[i].a;
			} else {
				return randomSize[i].b;
			}
		}
		return randomSize[9].b;
	}

	public Mine generateMap(float coverage, int coeff) {
		mine = new Mine(getRandomSize(), getRandomSize());
		mine.setStart(getMineSizeX(), getMineSizeY());
		mine.getTile(mine.getStartX(), mine.getStartY()).wall = false;
		genTile = mine.getTile(mine.getStartX(), mine.getStartY());
		while (mine.tileCoverage() < coverage) {
			genDirection = Direction.get(MathUtils.random(3));
			generateRoom();
		}
		while (mine.gasCoverage() < openTiles.size * coeff) {
			generateGas();
		}
		Mine genMine = mine;
		mine = null;
		return genMine;
	}

	private void generateGas() {
		Tile working, tile = openTiles.get(MathUtils.random(openTiles.size - 1));
		Array<Tile> gasTile = new Array<Tile>();
		int value = MathUtils.random(1, 10);
		if (tile.gas < value) {
			tile.gas = value;
			gasTile.add(tile);
			while (gasTile.size > 0) {
				tile = gasTile.removeIndex(0);
				for (Direction direction : Direction.values()) {
					working = mine.getTile(tile.posX + direction.offsetX, tile.posY + direction.offsetY);
					if (working != Mine.ERROR_TILE && working.gas < tile.gas - 1) {
						working.gas = tile.gas - 1;
						gasTile.add(working);
					}
				}
			}
		}
	}

	private void generateRoom() {
		Tile tile;
		int width = getMineSizeX() / 4;
		int height = getMineSizeY() / 4;
		int xStart = Math.max(0, genTile.posX - width), xEnd = Math.min(mine.getSizeX() - 1, genTile.posX + width);
		int yStart = Math.max(0, genTile.posX - height), yEnd = Math.min(mine.getSizeY() - 1, genTile.posX + height);

		for (int i = xStart; i < xEnd; i++) {
			for (int j = yStart; j < yEnd; j++) {
				tile = mine.getTile(i, j);
				tile.wall = false;
				if (!openTiles.contains(tile, true)) {
					openTiles.add(tile);
				}
			}
		}
		int move = Math.max(height, width) + MathUtils.random(3, 5);
		for (int i = 0; i < move; i++) {
			if (mine.getTile(genTile.posX + genDirection.offsetX, genTile.posY + genDirection.offsetY) != Mine.ERROR_TILE) {
				tile = mine.getTile(genTile.posX + genDirection.offsetX, genTile.posY + genDirection.offsetY);
				tile.wall = false;
				genTile = tile;
			} else {
				break;
			}
		}
	}

	public int getMineSizeX() {
		return MathUtils.random(mine.getSizeX() - 1);
	}

	public int getMineSizeY() {
		return MathUtils.random(mine.getSizeY() - 1);
	}

	public void createRoom(int posX, int posY) {
	}

	private class ValuePair<A, B> {
		public A a;
		public B b;

		public ValuePair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}
}
