package com.uprightpath.twentyeight.game;

import com.badlogic.gdx.utils.Array;
import com.uprightpath.twentyeight.TwentyEight;

public class GameHolder {
	private TwentyEight game;
	private Player player = new Player(10, 3);;
	private Mine mine;
	private MineGenerator mineGenerator = new MineGenerator();
	public int kingWins = 0;
	public int marriageWins = 0;

	public GameHolder(TwentyEight game) {
		this.game = game;
	}

	public boolean isPlaying() {
		return mine != null;
	}

	public void update() {
		player.update();
		mine.update();
		if (mine.hasWon()) {
			if (player.maxLost > 2) {
				marriageWins++;
			} else {
				kingWins++;
			}
			mine = null;


			if (marriageWins == 5) {
				game.setScreen(TwentyEight.GameScreens.WON);
			} else if (kingWins == 5) {
				game.setScreen(TwentyEight.GameScreens.LOST);
			} else {
				game.setScreen(TwentyEight.GameScreens.STATUS);
			}
		}
		if(player.health == 0) {
			game.setScreen(TwentyEight.GameScreens.LOST);
		}
	}

	public void setMine(Mine mine) {
		this.mine = mine;
		player.setMine(mine);
	}

	public Array<Mine> getMines() {
		Array<Mine> mines = new Array<Mine>();
		mines.add(mineGenerator.generateMap(.3f, 3));
		mines.add(mineGenerator.generateMap(.4f, 4));
		mines.add(mineGenerator.generateMap(.2f, 5));
		mines.add(mineGenerator.generateMap(.4f, 4));
		return mines;
	}

	public void playRandom() {
		this.setMine(mineGenerator.generateMap(.6f, 3));
	}

	public void candle() {
		if (player.candle()) {
			update();
		}
	}

	public void fling() {
		if (player.fling()) {
			mine.getDispatcher().ragFling();
			update();
		}
	}

	public void addListener(MineEventListener listener) {
		mine.getDispatcher().addListener(listener);
	}

	public void turn(Direction direction) {
		player.turn(direction);
	}

	public void move(Direction direction) {
		if (player.move(direction)) {
			update();
		}
	}

	public Mine getMine() {
		return mine;
	}

	public Player getPlayer() {
		return player;
	}

	public void restart() {
		player.maxHealth = 10;
		kingWins = 0;
		marriageWins = 0;
	}
}
