package com.uprightpath.contagion.screen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.uprightpath.contagion.Contagion;

public abstract class GermBackScreen extends ContagionScreen {
	private Animation transition;
	private Animation redVirus;
	private Animation brownVirus;
	private Animation current;
	private float time;
	private float wait = 3f;
	private boolean dir = true;

	public GermBackScreen(Contagion contagion) {
		super(contagion);
		transition = new Animation(1f / 10f, contagion.gameAtlas.createSprites("contagion"));
		transition.setPlayMode(Animation.NORMAL);
		redVirus = new Animation(1f / 20f, contagion.gameAtlas.createSprites("red"));
		redVirus.setPlayMode(Animation.LOOP);
		brownVirus = new Animation(1f / 20f, contagion.gameAtlas.createSprites("brown"));
		brownVirus.setPlayMode(Animation.LOOP_PINGPONG);
		current = brownVirus;
	}

	@Override
	protected void renderGraphics(float delta) {
		time += delta;
		if (current == brownVirus && time > wait) {
			time = 0;
			dir = true;
			current = transition;
		} else if (current == transition && transition.isAnimationFinished(time)) {
			time = 0;
			if (dir) {
				current = redVirus;
			} else {
				current = brownVirus;
			}
		} else if (current == redVirus && time > wait) {
			time = 0;
			dir = false;
			current = transition;
		}
		contagion.batch.draw(current.getKeyFrame(time), -400, -240, 800, 480);
	}

}
