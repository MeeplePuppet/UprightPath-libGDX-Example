package com.uprightpath.twentyeight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uprightpath.twentyeight.TwentyEight;

public abstract class TwentyEightScreen implements Screen {
	protected TwentyEight game;
	protected Stage stage;

	public TwentyEightScreen(TwentyEight game) {
		this.game = game;
		this.stage = new Stage();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glLineWidth(3);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		renderGraphics(delta);
		stage.act(delta);
		stage.draw();
	}

	public abstract void renderGraphics(float delta);

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

}
