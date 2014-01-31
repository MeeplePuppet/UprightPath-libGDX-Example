package com.uprightpath.contagion.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uprightpath.contagion.Contagion;

public class MainScreen extends GermBackScreen {
	private TextButton continueButton;

	public MainScreen(Contagion contagion) {
		super(contagion);
		Table table = new Table();
		stage.addActor(table);
		TextButton startButton = new TextButton("Start Game", contagion.skin, "default");
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MainScreen.this.contagion.changeScreen(Contagion.ScreenType.GAME_SELECT);
			}
		});
		table.add(startButton).center().fill(true, false).pad(2f).row();
		continueButton = new TextButton("Continue Game", contagion.skin, "default");
		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MainScreen.this.contagion.changeScreen(Contagion.ScreenType.GAME);
			}
		});
		table.add(continueButton).center().fill(true, false).pad(2f).row();
		TextButton tutorialButton = new TextButton("Tutorial", contagion.skin, "default");
		tutorialButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MainScreen.this.contagion.changeScreen(Contagion.ScreenType.TUTORIAL);
			}
		});
		table.add(tutorialButton).center().fill(true, false).pad(2f).row();
		TextButton aboutButton = new TextButton("About", contagion.skin, "default");
		aboutButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MainScreen.this.contagion.changeScreen(Contagion.ScreenType.ABOUT);
			}
		});
		table.add(aboutButton).center().fill(true, false).pad(2f).row();
		TextButton exitButton = new TextButton("Exit", contagion.skin, "default");
		exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		table.add(exitButton).center().fill(true, false).pad(2f);
		table.pack();
		table.setPosition(780 - table.getWidth(), 400 - table.getHeight());
	}

	@Override
	protected void renderGraphics(float delta) {
		super.renderGraphics(delta);
	}

	@Override
	public void show() {
		if (contagion.world != null && !contagion.world.hasLost()) {
			continueButton.setDisabled(false);
		} else {
			continueButton.setDisabled(true);
		}
		Gdx.input.setInputProcessor(stage);
	}
}
