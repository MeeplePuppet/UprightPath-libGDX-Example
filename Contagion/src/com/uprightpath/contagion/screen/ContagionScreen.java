package com.uprightpath.contagion.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.uprightpath.contagion.Contagion;
import com.uprightpath.contagion.Contagion.ScreenType;

public abstract class ContagionScreen implements Screen {
	protected Contagion contagion;
	protected Stage stage;

	public ContagionScreen(Contagion contagion) {
		this.contagion = contagion;
		this.stage = new Stage();
		this.stage.setCamera(contagion.camera);
	}

	@Override
	public final void render(float delta) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glLineWidth(3);
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		contagion.batch.begin();
		renderGraphics(delta);
		contagion.batch.end();
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	protected abstract void renderGraphics(float delta);

	public void resize(int width, int height) {
		stage.setCamera(contagion.camera);
		stage.setViewport(800, 480);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		if (!contagion.manager.update()) {
			contagion.changeScreen(ScreenType.LOADING);
		}
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
