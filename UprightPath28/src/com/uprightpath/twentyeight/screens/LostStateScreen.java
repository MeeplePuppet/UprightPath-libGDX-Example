package com.uprightpath.twentyeight.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.uprightpath.twentyeight.TwentyEight;

public class LostStateScreen extends TwentyEightScreen {
	private Table table;
	private Label lostLabel;

	public LostStateScreen(TwentyEight game) {
		super(game);
		table = new Table(game.skin);
		table.add("You've lost!").row();
		lostLabel = new Label("", game.skin);
		table.add(lostLabel).row();
		Button startButton = new TextButton("Restart", game.skin);
		startButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				LostStateScreen.this.newGame();
			}
		});
		table.add(startButton);
		table.setFillParent(true);
		stage.addActor(table);

	}

	protected void newGame() {
		game.gameHolder.restart();
		game.setScreen(TwentyEight.GameScreens.STATUS);
	}

	public void show() {
		super.show();
		lostLabel.setText(game.gameHolder.kingWins == 5 ? "You became the king, bad luck. It's a hard life." : "You burned to death.");
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
