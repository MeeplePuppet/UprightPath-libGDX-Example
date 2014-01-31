package com.uprightpath.twentyeight.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.twentyeight.game.Mine;
import com.uprightpath.twentyeight.game.MineEventListener;
import com.uprightpath.twentyeight.game.Player;
import com.uprightpath.twentyeight.game.Tile;

public class GameRenderer implements MineEventListener {
	private Player player;
	private Mine mine;
	private Material wallMaterial;
	private Material gasMaterial;
	private Environment environment;
	private Model wallModel;
	private Model floorModel;
	private Model playerModel;
	private Model[] gasModels = new Model[10];
	private Model[] fireModels = new Model[10];
	private TileRenderer[][] tileRenderers;
	private ModelInstance playerInstance;

	public GameRenderer(Player player, Mine mine) {
		this.player = player;
		this.mine = mine;
		this.mine.getDispatcher().addListener(this);
		wallMaterial = new Material(ColorAttribute.createDiffuse(Color.WHITE));
		gasMaterial = new Material(ColorAttribute.createDiffuse(new Color(0, 1f, 0, .5f)));
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		generateWalls();
		tileRenderers = new TileRenderer[mine.getSizeX() + 2][mine.getSizeY() + 2];
		for (int i = 0; i < tileRenderers.length; i++) {
			for (int j = 0; j < tileRenderers[i].length; j++) {
				tileRenderers[i][j] = new TileRenderer(mine.getTile(i - 1, j - 1), i - 1, j - 1);
			}
		}
		playerInstance = new ModelInstance(playerModel);
	}

	public void render(ModelBatch modelBatch, float delta) {
		for (int i = 0; i < tileRenderers.length; i++) {
			for (int j = 0; j < tileRenderers[i].length; j++) {
				tileRenderers[i][j].render(modelBatch);
			}
		}
		modelBatch.render(playerInstance, environment);
	}

	public void update() {
		playerInstance.transform.setToRotation(Vector3.Y, 0);
		playerInstance.transform.setTranslation(new Vector3(player.posX, 0, player.posY));
		playerInstance.transform.rotate(Vector3.Y, player.direction.degree);
	}

	public void generateWalls() {
		float val;
		ModelBuilder modelBuilder = new ModelBuilder();
		MeshPartBuilder partBuilder;
		for (int i = 0; i < 10; i++) {
			val = (i + 1) * .1f;
			modelBuilder.begin();
			partBuilder = modelBuilder.part("gas", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, gasMaterial);
			partBuilder.setColor(0, 1f, 0, .1f);
			partBuilder.box(1f, val, 1f);
			gasModels[i] = modelBuilder.end();
			modelBuilder.begin();
			partBuilder = modelBuilder.part("gas", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
			partBuilder.setColor(1f, 0, 0, 1f);
			partBuilder.box(1f, val, 1f);
			fireModels[i] = modelBuilder.end();
		}
		modelBuilder.begin();
		partBuilder = modelBuilder.part("top", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(Color.BLACK);
		partBuilder.rect(new Vector3(-.5f, .5f, .5f), new Vector3(.5f, .5f, .5f), new Vector3(.5f, .5f, -.5f), new Vector3(-.5f, .5f, -.5f), new Vector3(0, 1, 0));
		partBuilder = modelBuilder.part("walls", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(new Color(139f / 256f, 69f / 256f, 19 / 356f, 1f));
		partBuilder.rect(new Vector3(-.5f, .5f, .5f), new Vector3(-.5f, -.5f, .5f), new Vector3(.5f, -.5f, .5f), new Vector3(.5f, .5f, .5f), new Vector3(0, 0, 1));
		partBuilder.rect(new Vector3(.5f, .5f, -.5f), new Vector3(.5f, -.5f, -.5f), new Vector3(-.5f, -.5f, -.5f), new Vector3(-.5f, .5f, -.5f), new Vector3(0, 0, -1));
		partBuilder.rect(new Vector3(.5f, -.5f, .5f), new Vector3(.5f, -.5f, -.5f), new Vector3(.5f, .5f, -.5f), new Vector3(.5f, .5f, .5f), new Vector3(1, 0, 0));
		partBuilder.rect(new Vector3(-.5f, .5f, .5f), new Vector3(-.5f, .5f, -.5f), new Vector3(-.5f, -.5f, -.5f), new Vector3(-.5f, -.5f, .5f), new Vector3(-1, 0, 0));
		wallModel = modelBuilder.end();
		modelBuilder.begin();
		partBuilder = modelBuilder.part("top", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(new Color(222f / 256f, 184f / 256f, 135f / 256f, 1f));
		partBuilder.rect(new Vector3(-.5f, -.5f, .5f), new Vector3(.5f, -.5f, .5f), new Vector3(.5f, -.5f, -.5f), new Vector3(-.5f, -.5f, -.5f), new Vector3(0, 1, 0));
		floorModel = modelBuilder.end();
		modelBuilder.begin();
		partBuilder = modelBuilder.part("hat", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(new Color(139f / 256f, 69f / 256f, 19 / 356f, 1f));
		partBuilder.cone(.55f, .6f, .55f, 8);
		Model hat = modelBuilder.end();
		modelBuilder.begin();
		partBuilder = modelBuilder.part("body", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(new Color(139f / 256f, 69f / 256f, 19 / 356f, 1f));
		partBuilder.capsule(.3f, 1f, 8);
		Model body = modelBuilder.end();
		modelBuilder.begin();
		partBuilder = modelBuilder.part("lantern", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(new Color(139f / 256f, 69f / 256f, 19 / 356f, 1f));
		partBuilder.cylinder(.1f, 1.5f, .1f, 3);
		Model stick = modelBuilder.end();
		modelBuilder.begin();
		partBuilder = modelBuilder.part("lantern", GL20.GL_TRIANGLES, Usage.Color | Usage.Position | Usage.Normal, wallMaterial);
		partBuilder.setColor(Color.YELLOW);
		partBuilder.cylinder(.2f, .3f, .2f, 8);
		Model lantern = modelBuilder.end();
		modelBuilder.begin();
		modelBuilder.node("hat", hat);
		modelBuilder.node("body", body);
		modelBuilder.node("stick", stick);
		modelBuilder.node("lantern", lantern);
		playerModel = modelBuilder.end();
		playerModel.getNode("hat").translation.add(0, .68f, .175f);
		playerModel.getNode("hat").rotation.set(Vector3.X, 20);
		playerModel.getNode("stick").translation.add(-.3f, .6f, -.3f);
		playerModel.getNode("lantern").translation.add(.3f, .2f, -.3f);
		playerModel.calculateTransforms();
	}

	public class TileRenderer {
		private Tile tile;
		private ModelInstance tileInstance;
		private ModelInstance[] gasInstance = new ModelInstance[10];
		private ModelInstance[] fireInstance = new ModelInstance[10];

		public TileRenderer(Tile tile, int posX, int posY) {
			this.tile = tile;
			if (tile.wall) {
				tileInstance = new ModelInstance(wallModel);
			} else {
				tileInstance = new ModelInstance(floorModel);
				for (int i = 0; i < 10; i++) {
					gasInstance[i] = new ModelInstance(gasModels[i]);
					gasInstance[i].transform.translate(posX, (10 - i) * -.05f, posY);
					fireInstance[i] = new ModelInstance(fireModels[i]);
					fireInstance[i].transform.translate(posX,  (10 - i) * -.05f, posY);
				}
			}
			tileInstance.transform.translate(posX, 0, posY);
		}

		public void render(ModelBatch modelBatch) {
			modelBatch.render(tileInstance, environment);
			if (!tile.wall) {
				if (tile.ignited && tile.fire > 0) {
					modelBatch.render(fireInstance[tile.fire - 1], environment);
				} else if (!tile.ignited && tile.gas > 0) {
					modelBatch.render(gasInstance[tile.gas - 1], environment);
				}
			}
		}
	}

	public void dispose() {
		wallModel.dispose();
		floorModel.dispose();
		for (int i = 0; i < 10; i++) {
			gasModels[i].dispose();
			fireModels[i].dispose();
		}
		mine.getDispatcher().removeListener(this);
	}

	@Override
	public void ragFling() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ragBurnt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void candle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ignites() {
		// TODO Auto-generated method stub

	}

	@Override
	public void burnedOut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerBurned() {
		// TODO Auto-generated method stub

	}
}
