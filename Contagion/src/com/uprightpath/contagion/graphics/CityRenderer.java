package com.uprightpath.contagion.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.uprightpath.contagion.logic.City;

public class CityRenderer {
	public final WorldRenderer worldRenderer;
	public final City city;
	public Animation cityImage;
	public Animation timer;
	public Rectangle rect = new Rectangle();

	public CityRenderer(WorldRenderer worldRenderer, City city) {
		this.worldRenderer = worldRenderer;
		this.city = city;
		cityImage = new Animation(1, worldRenderer.contagion.gameAtlas.createSprites("city"));
		timer = new Animation(1f / 17f, worldRenderer.contagion.gameAtlas.createSprites("bar"));
	}

	private int getCityFrame() {
		if (city.team == null) {
			if (city.infectionCoeff > 8) {
				return 3;
			} else if (city.infectionCoeff > 4) {
				return 2;
			} else if (city.infectionCoeff > 2) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 4;
		}
	}

	private float getTimerFrame() {
		if (city.team == null) {
			return ((float) city.infectionLevel) / 100f;
		} else {
			return ((float) city.team.timeDone) / ((float) worldRenderer.contagion.world.cureTime);
		}
	}

	public boolean touch(float x, float y) {
		if (rect.contains(x, y)) {
			worldRenderer.contagion.world.assignCDCTeam(city);
			return true;
		}
		return false;
	}

	public void draw(Batch batch, float delta) {
		batch.draw(cityImage.getKeyFrame(getCityFrame()), rect.x, rect.y);
		batch.draw(timer.getKeyFrame(getTimerFrame()), rect.x + 7, rect.y + 2);
	}

	public void resize(float width, float height) {
		rect.set(width * city.x, height * city.y, 32, 32);
		System.out.println(rect);
	}
}
