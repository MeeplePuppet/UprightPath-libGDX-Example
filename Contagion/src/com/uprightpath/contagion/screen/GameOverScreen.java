package com.uprightpath.contagion.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uprightpath.contagion.Contagion;

public class GameOverScreen extends GermBackScreen {
	private Label aliveLabel;

	public GameOverScreen(Contagion contagion) {
		super(contagion);
		Table table = new Window("Game Over", contagion.skin);
		table.add("The World lasted").pad(2).row();
		aliveLabel = new Label("Alive?", contagion.skin);
		table.add(aliveLabel).pad(2).row();
		table.add("after the outbreak.").pad(2).row();
		TextButton continueButton = new TextButton("Continue", contagion.skin, "default");
		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameOverScreen.this.contagion.world = null;
				GameOverScreen.this.contagion.changeScreen(Contagion.ScreenType.MAIN);
			}
		});
		table.add(continueButton).pad(2f).row();
		table.pack();
		table.setPosition(400 - table.getWidth() / 2, 240 - table.getHeight() / 2);
		stage.addActor(table);
	}

	@Override
	protected void renderGraphics(float delta) {
		super.renderGraphics(delta);
	}

	@Override
	public void show() {
		aliveLabel.setText(contagion.world.worldAlive + " days");
		Gdx.input.setInputProcessor(stage);
	}
}
