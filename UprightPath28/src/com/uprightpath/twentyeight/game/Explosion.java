package com.uprightpath.twentyeight.game;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Explosion {
	private Mine mine;
	private Array<Tile> tiles = new Array<Tile>();
	private Array<Tile> explodingTiles = new Array<Tile>();
	private Array<Tile> scratchTiles = new Array<Tile>();
	private int totalGas;
	private boolean over = false;

	public Explosion(Mine mine, Tile tile) {
		this.mine = mine;
		explodingTiles.add(tile);
		tile.fire = tile.gas;
		totalGas += tile.gas;
		tiles.add(tile);
	}

	public boolean update() {
		boolean spread = false;
		Iterator<Tile> iterator;
		Tile tile;
		if (over) {
			iterator = tiles.iterator();
			while (iterator.hasNext()) {
				tile = iterator.next();
				if (!explodingTiles.contains(tile, true)) {
					if (tile.fire-- <= 0) {
						totalGas -= tile.gas;
						iterator.remove();
					}
				}
			}
		} else {
			int burns = totalGas / (tiles.size + explodingTiles.size);
			for (int i = 0; i < explodingTiles.size; i++) {
				tile = explodingTiles.get(i);
				if (!tile.ignited) {
					spread = true;
					tile.fire = MathUtils.clamp(Math.max(burns, tile.gas), 0, 10);
				}
				tile.ignited = true;
				if (!tiles.contains(tile, true)) {
					tiles.add(tile);
				}
				addAdjacent(mine.getTile(tile.posX - 1, tile.posY));
				addAdjacent(mine.getTile(tile.posX + 1, tile.posY));
				addAdjacent(mine.getTile(tile.posX, tile.posY - 1));
				addAdjacent(mine.getTile(tile.posX, tile.posY + 1));
			}
			iterator = tiles.iterator();
			while (iterator.hasNext()) {
				tile = iterator.next();
				if (tile.fire-- <= 0) {
					totalGas -= tile.gas;
					iterator.remove();
				}
			}
			if (tiles.size > 0) {
				burns = totalGas / tiles.size;
				explodingTiles.clear();
				if (burns > 1) {
					explodingTiles.addAll(scratchTiles);
					scratchTiles.clear();
				} else {
					over = true;
				}
			}
		}
		return spread;
	}

	public void addAdjacent(Tile tile) {
		if (!tile.wall && !scratchTiles.contains(tile, true) && !tile.ignited) {
			totalGas += tile.gas;
			scratchTiles.add(tile);
		}
	}

	public boolean isOver() {
		return tiles.size == 0;
	}
}
