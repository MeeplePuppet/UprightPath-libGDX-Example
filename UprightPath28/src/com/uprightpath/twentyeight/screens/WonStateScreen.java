package com.uprightpath.twentyeight.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.uprightpath.twentyeight.TwentyEight;

public class WonStateScreen extends TwentyEightScreen {
	private Table table;
	private Label wonLabel;
	private Label kingLabel;

	public WonStateScreen(TwentyEight game) {
		super(game);
		table = new Table(game.skin);
		Label label = new Label("You've got the girl of your dreams! Good job, retire!", game.skin);
		label.setWrap(true);
		table.add(label);
		Button startButton = new TextButton("Restart", game.skin);
		startButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				WonStateScreen.this.newGame();
			}
		});
		table.add(startButton);
		table.setFillParent(true);
		stage.addActor(table);
	}

	protected void newGame() {
		game.gameHolder.restart();
		game.setScreen(TwentyEight.GameScreens.PLAY);
	}

	public void show() {
		super.show();
		wonLabel.setText((5 - game.gameHolder.marriageWins) + "");
		kingLabel.setText((5 - game.gameHolder.kingWins) + "");
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height);
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void renderGraphics(float delta) {
	}

}
