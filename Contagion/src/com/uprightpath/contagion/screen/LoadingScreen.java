package com.uprightpath.contagion.screen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.uprightpath.contagion.Contagion;

public class LoadingScreen extends ContagionScreen {
	private Sprite background;
	private Animation animation;
	private float time;

	public LoadingScreen(Contagion contagion) {
		super(contagion);
		background = contagion.loadingAtlas.createSprite("contagion");
		animation = new Animation(1f / 5f, contagion.loadingAtlas.createSprites("loading"));
		animation.setPlayMode(Animation.LOOP);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	protected void renderGraphics(float delta) {
		time += delta;
		background.setBounds(-400, -240, 800, 480);
		background.draw(contagion.batch);
		contagion.batch.draw(animation.getKeyFrame(time), 80, 135);
		if (contagion.manager.update()) {
			contagion.doneLoading();
			contagion.changeScreen(Contagion.ScreenType.MAIN);
		}
	}

	@Override
	public void show() {
	}
}
