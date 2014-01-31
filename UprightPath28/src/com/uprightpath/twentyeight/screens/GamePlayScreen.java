package com.uprightpath.twentyeight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.uprightpath.twentyeight.TwentyEight;
import com.uprightpath.twentyeight.game.Direction;
import com.uprightpath.twentyeight.game.Mine;
import com.uprightpath.twentyeight.game.MineEventListener;
import com.uprightpath.twentyeight.game.Player;
import com.uprightpath.twentyeight.graphics.GameRenderer;

public class GamePlayScreen extends TwentyEightScreen implements
		MineEventListener {
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private GameRenderer gameRenderer;
	private Table hudTable;
	private Table leftControls;
	private Table rightControls;
	private Table mainUI;
	private Label timeLabel;
	private Button soundButton;
	private Button waitButton;
	private Button faceButton;
	private Button candleButton;
	private Button flingButton;
	private Button[] moveButtons = new Button[4];
	private Image soundImage;
	private Image[] health = new Image[10];
	private Image[] rags = new Image[3];

	public GamePlayScreen(TwentyEight game) {
		super(game);
		modelBatch = new ModelBatch();
		waitButton = new Button(game.skin);
		waitButton.add(new Image(game.gameAtlas.findRegion("wait")));
		waitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.game.gameHolder.update();
			}
		});
		faceButton = new Button(game.skin);
		faceButton.add(new Image(game.gameAtlas.findRegion("face")));
		candleButton = new Button(game.skin);
		candleButton.add(new Image(game.gameAtlas.findRegion("candle")));
		candleButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.game.gameHolder.candle();
			}
		});
		flingButton = new Button(game.skin);
		flingButton.add(new Image(game.gameAtlas.findRegion("fling")));
		flingButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.game.gameHolder.fling();
			}
		});
		for (int i = 0; i < 4; i++) {
			moveButtons[i] = new Button(game.skin);
			moveButtons[i].add(new Image(game.gameAtlas.findRegion("control", i)));
		}
		moveButtons[0].addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.control(Direction.RIGHT);

			}
		});
		moveButtons[1].addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.control(Direction.UP);
			}
		});
		moveButtons[2].addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.control(Direction.LEFT);
			}
		});
		moveButtons[3].addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.control(Direction.DOWN);
			}
		});
		for (int i = 0; i < 10; i++) {
			health[i] = new Image(game.gameAtlas.findRegion("health", 0));
		}
		for (int i = 0; i < 3; i++) {
			rags[i] = new Image(game.gameAtlas.findRegion("rag", 0));
		}
		timeLabel = new Label("Turns: 0", game.skin);
		soundButton = new Button(game.skin);
		soundImage = new Image(game.gameAtlas.findRegion("sound", 0));
		soundButton.add(soundImage);
		soundButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePlayScreen.this.game.sound.setMute(soundButton.isChecked());
				GamePlayScreen.this.game.music.setMute(soundButton.isChecked());
				if (!soundButton.isChecked()) {
					soundImage.setDrawable(new TextureRegionDrawable(GamePlayScreen.this.game.gameAtlas.findRegion("sound", 0)));
				} else {
					soundImage.setDrawable(new TextureRegionDrawable(GamePlayScreen.this.game.gameAtlas.findRegion("sound", 1)));
				}
			}
		});

		hudTable = new Table(game.skin);
		hudTable.setBackground(game.skin.getDrawable("default-round"));
		hudTable.add("HP:");
		for (int i = 0; i < 10; i++) {
			hudTable.add(health[i]);
		}
		hudTable.add("Fire:");
		for (int i = 0; i < 3; i++) {
			hudTable.add(rags[i]);
		}
		hudTable.add("Time:");
		hudTable.add(timeLabel);
		hudTable.add().expandX().fillX();
		hudTable.add(soundButton);

		rightControls = new Table();
		rightControls.add(candleButton);
		rightControls.add(flingButton).row();
		rightControls.add(waitButton);
		rightControls.add(faceButton);
		rightControls.add();
		rightControls.pack();
		leftControls = new Table();
		leftControls.add(moveButtons[0]);
		leftControls.add(moveButtons[1]).row();
		leftControls.add(moveButtons[3]);
		leftControls.add(moveButtons[2]);
		leftControls.pack();
		mainUI = new Table();
		mainUI.add(hudTable).fillX().expandX().colspan(3).row();
		mainUI.add().expandY().fillY().row();
		mainUI.add(leftControls);
		mainUI.add().fillX().expandX();
		mainUI.add(rightControls);
		mainUI.pack();
		mainUI.setFillParent(true);
		stage.addActor(mainUI);
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		gameRenderer.dispose();
	}

	@Override
	public void renderGraphics(float delta) {
		modelBatch.begin(camera);
		gameRenderer.render(modelBatch, Gdx.graphics.getDeltaTime());
		modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height);
		camera = new PerspectiveCamera(67, width, height);
		camera.position.set(-2, 5, -2);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();
		update();
	}

	private void control(Direction direction) {
		if (faceButton.isPressed()) {
			game.gameHolder.turn(direction);
		} else {
			game.gameHolder.move(direction);
		}
	}

	public void update() {
		camera.position.set(game.gameHolder.getPlayer().posX - 2, 5, game.gameHolder.getPlayer().posY - 2);
		camera.update();
		gameRenderer.update();
		timeLabel.setText(game.gameHolder.getMine().getTime() + "");
	}

	@Override
	public void ragFling() {
		int value = game.gameHolder.getPlayer().rags;
		for (; value < game.gameHolder.getPlayer().maxRags; value++) {
			rags[value].setDrawable(new TextureRegionDrawable(GamePlayScreen.this.game.gameAtlas.findRegion("rag", 1)));
		}
	}

	@Override
	public void ragBurnt() {
	}

	@Override
	public void candle() {
	}

	@Override
	public void ignites() {
		game.sound.playSound("explosion");
	}

	@Override
	public void burnedOut() {

	}

	@Override
	public void playerBurned() {
		int value = game.gameHolder.getPlayer().health;
		for (; value < game.gameHolder.getPlayer().maxHealth; value++) {
			health[value].setDrawable(new TextureRegionDrawable(GamePlayScreen.this.game.gameAtlas.findRegion("health", 1)));
		}
		for (; value < 10; value++) {
			health[value].setDrawable(new TextureRegionDrawable(GamePlayScreen.this.game.gameAtlas.findRegion("health", 2)));
		}
	}

	public void show() {
		super.show();
		gameRenderer = new GameRenderer(game.gameHolder.getPlayer(), game.gameHolder.getMine());
		game.gameHolder.addListener(this);
		playerBurned();
	}
}
