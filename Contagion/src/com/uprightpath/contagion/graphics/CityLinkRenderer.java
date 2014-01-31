package com.uprightpath.contagion.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.contagion.logic.City;

public class CityLinkRenderer {
	public WorldRenderer worldRenderer;
	public CityRenderer first;
	public CityRenderer second;
	public Vector2 firstVec = new Vector2();
	public Vector2 secondVec = new Vector2();

	public CityLinkRenderer(WorldRenderer worldRenderer, CityRenderer first, CityRenderer second) {
		this.worldRenderer = worldRenderer;
		this.first = first;
		this.second = second;
	}

	public Color getColor(City city) {
		if (city.team != null) {
			return Color.BLUE;
		} else if (city.infectionLevel == 100) {
			return Color.RED;
		} else if (city.infectionLevel > 66) {
			return Color.ORANGE;
		} else if (city.infectionLevel > 33) {
			return Color.YELLOW;
		} else {
			return Color.GREEN;
		}
	}

	public void draw(Batch batch, float delta) {
		first.rect.getCenter(firstVec);
		second.rect.getCenter(secondVec);
		worldRenderer.contagion.shapeRenderer.line(firstVec.x, first.rect.y + 2, secondVec.x, second.rect.y + 2, getColor(first.city), getColor(second.city));
	}
}
