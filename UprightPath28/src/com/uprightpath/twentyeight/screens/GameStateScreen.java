package com.uprightpath.twentyeight.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.uprightpath.twentyeight.TwentyEight;

public class GameStateScreen extends TwentyEightScreen {
	private Table table;
	private Label wonLabel;
	private Label kingLabel;

	public GameStateScreen(TwentyEight game) {
		super(game);
		table = new Table(game.skin);
		Label label = new Label("Knocker men become kings. They go down into the mines and hear the creaking of the props and ignite the fire damp. But you? You want to get married to win the eye of a special girl. Living smart (Taking no damage) makes you king; living dangerously (Taking damage) gets you the girl. What will your life be like?", game.skin);
		label.setWrap(true);
		table.add(label).colspan(3).row();
		table.add("Kingship in: ");
		kingLabel = new Label("5", game.skin);
		table.add(kingLabel);
		table.add("smart wins.").row();
		table.add("Marriage in: ");
		wonLabel = new Label("5", game.skin);
		table.add(wonLabel);
		table.add("smart wins.").row();
		Button startButton = new TextButton("Enter Mine", game.skin);
		startButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameStateScreen.this.newGame();
			}
		});
		table.add(startButton).colspan(3);
		table.setFillParent(true);
		stage.addActor(table);
	}

	protected void newGame() {
		game.gameHolder.playRandom();
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
