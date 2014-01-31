package com.uprightpath.twentyeight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uprightpath.twentyeight.TwentyEight.GameScreens;
import com.uprightpath.twentyeight.game.GameHolder;
import com.uprightpath.twentyeight.screens.GamePlayScreen;
import com.uprightpath.twentyeight.screens.GameStateScreen;
import com.uprightpath.twentyeight.screens.LostStateScreen;
import com.uprightpath.twentyeight.screens.MainMenuScreen;
import com.uprightpath.twentyeight.screens.TwentyEightScreen;
import com.uprightpath.twentyeight.screens.WonStateScreen;

public class TwentyEight extends Game {
	public enum GameScreens {
		MAIN, STATUS, PLAY, WON, LOST;
	}

	public final AssetManager manager = new AssetManager();
	public GameHolder gameHolder;
	public SoundManager sound;
	public MusicManager music;
	public Camera camera;
	public SpriteBatch batch;
	public Skin skin;
	public TextureAtlas loadingAtlas;
	public TextureAtlas gameAtlas;
	public BitmapFont font;
	private TwentyEightScreen[] screens;

	@Override
	public void create() {
		Texture.setAssetManager(manager);
		batch = new SpriteBatch();
		manager.load("data/graphics/game.json", Skin.class, new SkinLoader.SkinParameter("data/graphics/game.atlas"));
		manager.load("data/sfx/explosion_1.wav", Sound.class);
		manager.load("data/sfx/explosion_2.wav", Sound.class);
		manager.load("data/sfx/explosion_3.wav", Sound.class);
		manager.load("data/sfx/hurt_1.wav", Sound.class);
		manager.load("data/sfx/hurt_2.wav", Sound.class);
		manager.load("data/sfx/hurt_3.wav", Sound.class);
		manager.load("data/sfx/shoot_1.wav", Sound.class);
		manager.load("data/sfx/shoot_2.wav", Sound.class);
		manager.load("data/sfx/shoot_3.wav", Sound.class);
		manager.load("data/music/main.mp3", Music.class);
		manager.load("data/music/mine.mp3", Music.class);
		manager.finishLoading();
		skin = manager.get("data/graphics/game.json", Skin.class);
		gameAtlas = manager.get("data/graphics/game.atlas", TextureAtlas.class);
		font = skin.getFont("default-font");

		sound = new SoundManager();
		sound.addSound("explosion", manager.get("data/sfx/explosion_1.wav", Sound.class));
		sound.addSound("explosion", manager.get("data/sfx/explosion_2.wav", Sound.class));
		sound.addSound("explosion", manager.get("data/sfx/explosion_3.wav", Sound.class));
		sound.addSound("hurt", manager.get("data/sfx/hurt_1.wav", Sound.class));
		sound.addSound("hurt", manager.get("data/sfx/hurt_2.wav", Sound.class));
		sound.addSound("hurt", manager.get("data/sfx/hurt_3.wav", Sound.class));
		sound.addSound("shoot", manager.get("data/sfx/shoot_1.wav", Sound.class));
		sound.addSound("shoot", manager.get("data/sfx/shoot_2.wav", Sound.class));
		sound.addSound("shoot", manager.get("data/sfx/shoot_3.wav", Sound.class));

		music = new MusicManager();
		music.addSound("main", manager.get("data/music/main.mp3", Music.class));
		music.addSound("mine", manager.get("data/music/mine.mp3", Music.class));
		music.playSound("main");
		gameHolder = new GameHolder(this);
		screens = new TwentyEightScreen[GameScreens.values().length];
		screens[GameScreens.PLAY.ordinal()] = new GamePlayScreen(this);
		screens[GameScreens.MAIN.ordinal()] = new MainMenuScreen(this);
		screens[GameScreens.STATUS.ordinal()] = new GameStateScreen(this);
		screens[GameScreens.WON.ordinal()] = new WonStateScreen(this);
		screens[GameScreens.LOST.ordinal()] = new LostStateScreen(this);
		setScreen(screens[GameScreens.MAIN.ordinal()]);
	}

	public void setScreen(GameScreens status) {
		setScreen(screens[status.ordinal()]);
	}
}
