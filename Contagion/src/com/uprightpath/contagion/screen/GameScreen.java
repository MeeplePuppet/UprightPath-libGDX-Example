package com.uprightpath.contagion.screen;

import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.contagion.Contagion;
import com.uprightpath.contagion.graphics.WorldRenderer;
import com.uprightpath.contagion.logic.CDCTeam;

public class GameScreen extends ContagionScreen {
	private InputMultiplexer multiplexer = new InputMultiplexer();
	private WorldRenderer worldRenderer;
	private float accumulator = 0;
	private float timer = 1f / 2f;
	private Button leftButton;
	private Button rightButton;
	private Label virusStatus;
	private Label daysAlive;
	private HashMap<CDCTeam, Image> cdcTeams = new HashMap<CDCTeam, Image>();
	private Array<Drawable> cdcTeamImage = new Array<Drawable>();
	private Table table;
	private Table cdcTeamTable;

	public GameScreen(Contagion contagion) {
		super(contagion);
		for (Sprite sprite : contagion.gameAtlas.createSprites("teams")) {
			cdcTeamImage.add(new TextureRegionDrawable(sprite));
		}
		table = new Table(contagion.skin);
		table.setBackground(contagion.skin.getDrawable("default-rect"));
		leftButton = new Button(contagion.skin);
		leftButton.add(new Image(contagion.gameAtlas.createSprite("needle")));
		leftButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (GameScreen.this.rightButton.isPressed() || Gdx.app.getType() == ApplicationType.Desktop) {
					GameScreen.this.contagion.world.globalCure();
				}
			}
		});
		table.add(leftButton).pad(2).left();
		Table hud = new Table(contagion.skin);
		hud.add("Virus:").pad(2);
		virusStatus = new Label("Normal", contagion.skin);
		hud.add(virusStatus).pad(2);
		hud.add().width(10);
		hud.add("Days:").pad(2);
		daysAlive = new Label("", contagion.skin);
		hud.add(daysAlive).pad(2);
		hud.add().width(10);
		hud.add("CDC Teams:").pad(2);
		cdcTeamTable = new Table();
		hud.add(cdcTeamTable);
		table.add(hud).center().fillX().expandX();
		rightButton = new Button(contagion.skin);
		rightButton.add(new Image(contagion.gameAtlas.createSprite("needle")));
		rightButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (GameScreen.this.leftButton.isPressed() || Gdx.app.getType() == ApplicationType.Desktop) {
					GameScreen.this.contagion.world.globalCure();
				}
			}
		});
		table.add(rightButton).pad(2).right();
		stage.addActor(table);
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	protected void renderGraphics(float delta) {
		accumulator += delta;
		while (accumulator > timer) {
			accumulator -= timer;
			contagion.world.update();
		}
		for (CDCTeam team : contagion.world.teams) {
			cdcTeams.get(team).setDrawable(cdcTeamImage.get(team.state.ordinal()));
		}
		if (contagion.world.mutated) {
			virusStatus.setColor(Color.RED);
			virusStatus.setText("Mutated (" + contagion.world.numCure + ")");
			leftButton.setDisabled(true);
			rightButton.setDisabled(true);
		} else {
			virusStatus.setText("Normal (" + contagion.world.numCure + ")");
			leftButton.setDisabled(false);
			rightButton.setDisabled(false);

		}
		daysAlive.setText("" + contagion.world.worldAlive);
		if (contagion.world.hasLost()) {
			contagion.changeScreen(Contagion.ScreenType.GAME_END);
		} else {
			worldRenderer.draw(contagion.batch, delta);
		}
	}

	@Override
	public void show() {
		Image image;
		worldRenderer = new WorldRenderer(contagion);
		multiplexer.clear();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new WorldEventProcessor(worldRenderer));
		Gdx.input.setInputProcessor(multiplexer);
		cdcTeamTable.clear();
		cdcTeams.clear();
		for (CDCTeam team : contagion.world.teams) {
			image = new Image(cdcTeamImage.first());
			cdcTeamTable.add(image).pad(2);
			cdcTeams.put(team, image);
		}
		for (int i = cdcTeams.size(); i < 4; i++) {
			cdcTeamTable.add(new Image(cdcTeamImage.get(3))).pad(2);
		}
		table.pack();
		table.setWidth(800);
		table.setPosition(0, 480 - table.getHeight());
	}

}
