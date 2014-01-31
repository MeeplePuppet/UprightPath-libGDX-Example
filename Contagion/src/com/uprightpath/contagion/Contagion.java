package com.uprightpath.contagion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uprightpath.contagion.loader.WorldDefinitionManagerLoader;
import com.uprightpath.contagion.logic.World;
import com.uprightpath.contagion.logic.WorldDefinitionManager;
import com.uprightpath.contagion.screen.*;

public class Contagion extends Game {
	public enum ScreenType {
		LOADING, MAIN, GAME_SELECT, GAME, GAME_END, TUTORIAL, ABOUT;
	}

	public final AssetManager manager = new AssetManager();
	public Camera camera;
	public SpriteBatch batch;
	public Screen[] screens = new Screen[7];
	public World world = null;
	public Skin skin;
	public TextureAtlas loadingAtlas;
	public TextureAtlas gameAtlas;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;
	public WorldDefinitionManager worldDefinitionManager;

	@Override
	public void create() {
		camera = new OrthographicCamera(800, 480);
		Texture.setAssetManager(manager);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.projection);
		manager.setLoader(WorldDefinitionManager.class, new WorldDefinitionManagerLoader(new InternalFileHandleResolver()));
		manager.load("data/loading.atlas", TextureAtlas.class);
		manager.finishLoading();
		loadingAtlas = manager.get("data/loading.atlas", TextureAtlas.class);
		manager.load("data/game.json", Skin.class, new SkinLoader.SkinParameter("data/game.atlas"));
		manager.load("data/worlds.xml", WorldDefinitionManager.class);
		screens[0] = new LoadingScreen(this);
		setScreen(screens[0]);
	}

	public void changeScreen(ScreenType type) {
		setScreen(screens[type.ordinal()]);
	}

	public void dispose() {
		super.dispose();
		manager.dispose();
	}

	public void doneLoading() {
		skin = manager.get("data/game.json", Skin.class);
		gameAtlas = manager.get("data/game.atlas", TextureAtlas.class);
		worldDefinitionManager = manager.get("data/worlds.xml", WorldDefinitionManager.class);
		font = skin.getFont("contagion-font");
		screens[ScreenType.MAIN.ordinal()] = new MainScreen(this);
		screens[ScreenType.GAME_SELECT.ordinal()] = new GameSelectScreen(this);
		screens[ScreenType.GAME.ordinal()] = new GameScreen(this);
		screens[ScreenType.GAME_END.ordinal()] = new GameOverScreen(this);
		screens[ScreenType.TUTORIAL.ordinal()] = new TutorialScreen(this);
		screens[ScreenType.ABOUT.ordinal()] = new AboutScreen(this);
	}
}
