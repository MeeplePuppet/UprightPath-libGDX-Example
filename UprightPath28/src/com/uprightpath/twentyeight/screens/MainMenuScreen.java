package com.uprightpath.twentyeight.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uprightpath.twentyeight.TwentyEight;

public class MainMenuScreen extends TwentyEightScreen {
	private Table mainMenu;
	private Button gameButton;
	private Button continueButton;
	private Button optionButton;

	public MainMenuScreen(TwentyEight game) {
		super(game);
		mainMenu = new Table(game.skin);
		mainMenu.setBackground(game.skin.getDrawable("default-round"));
		mainMenu.add("Fire Damp").row();
		mainMenu.add("Happiness, Kingship or Death!").row();
		gameButton = new TextButton("New Game", game.skin);
		gameButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MainMenuScreen.this.newGame();
			}
		});
		mainMenu.add(gameButton).row();
		mainMenu.setFillParent(true);
		stage.addActor(mainMenu);
	}


	protected void newGame() {
		game.setScreen(TwentyEight.GameScreens.STATUS);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderGraphics(float delta) {
		// TODO Auto-generated method stub

	}

}
