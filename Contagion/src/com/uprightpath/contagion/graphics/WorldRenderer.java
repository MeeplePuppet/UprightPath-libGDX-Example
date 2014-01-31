package com.uprightpath.contagion.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.contagion.Contagion;
import com.uprightpath.contagion.logic.City;
import com.uprightpath.contagion.logic.World;
import com.uprightpath.contagion.logic.WorldDefinition.CityLinkDefinition;

public class WorldRenderer {
	public Contagion contagion;
	public World world;
	private Array<CityRenderer> cityRenderer = new Array<CityRenderer>();
	private Array<CityLinkRenderer> linkRenderer = new Array<CityLinkRenderer>();
	private Sprite sprite;

	public WorldRenderer(Contagion contagion) {
		this.contagion = contagion;
		this.world = contagion.world;
		sprite = contagion.gameAtlas.createSprite(world.definition.background);
		sprite.setPosition(-400, -240);
		for (City city : world.cities) {
			cityRenderer.add(new CityRenderer(this, city));
		}
		for (CityLinkDefinition definition : world.definition.cityLinks) {
			linkRenderer.add(new CityLinkRenderer(this, cityRenderer.get(definition.first), cityRenderer.get(definition.second)));
		}
	}

	public void draw(Batch batch, float delta) {
		sprite.draw(batch);
		contagion.batch.end();
		// contagion.shapeRenderer.setTransformMatrix(contagion.camera.projection);
		contagion.shapeRenderer.setProjectionMatrix(contagion.camera.projection);
		contagion.shapeRenderer.begin(ShapeType.Line);
		for (int i = 0; i < linkRenderer.size; i++) {
			linkRenderer.get(i).draw(batch, delta);
		}
		contagion.shapeRenderer.end();
		contagion.batch.begin();
		for (int i = 0; i < cityRenderer.size; i++) {
			cityRenderer.get(i).draw(batch, delta);
		}
	}

	public void resize(float width, float height) {
		for (CityRenderer city : cityRenderer) {
			city.resize(width, height);
		}
	}

	public void touch(float x, float y) {
		Vector3 touchPoint = new Vector3(x, y, 0);
		contagion.camera.unproject(touchPoint);
		touchPoint.x -= 400;
		touchPoint.y -= 240;
		System.out.println(touchPoint);
		for (CityRenderer city : cityRenderer) {
			if (city.touch(touchPoint.x, touchPoint.y)) {
				break;
			}
		}
	}
}
