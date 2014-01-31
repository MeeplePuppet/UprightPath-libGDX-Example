package com.uprightpath.contagion.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.esotericsoftware.tablelayout.Cell;
import com.uprightpath.contagion.Contagion;

public class TutorialScreen extends GermBackScreen {
	private Table mainTable;
	private Table basicTable;
	private Table cityTable;
	private Table cdcTable;
	private Cell<Actor> mainCell;

	public TutorialScreen(Contagion contagion) {
		super(contagion);
		Label label;
		mainTable = new Window("Tutorial", contagion.skin);
		TextButton basicButton = new TextButton("Basics", contagion.skin, "toggle");
		basicButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TutorialScreen.this.mainCell.setWidget(basicTable);
				basicTable.pack();
			}
		});
		mainTable.add(basicButton).pad(2).fillX();
		TextButton cityButton = new TextButton("Cities", contagion.skin, "toggle");
		cityButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TutorialScreen.this.mainCell.setWidget(cityTable);
				basicTable.pack();
			}
		});
		mainTable.add(cityButton).pad(2).fillX();
		TextButton cdcButton = new TextButton("CDC", contagion.skin, "toggle");
		cdcButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				TutorialScreen.this.mainCell.setWidget(cdcTable);
				basicTable.pack();
			}
		});
		mainTable.add(cdcButton).pad(2).fillX().row();
		ButtonGroup buttonGroup = new ButtonGroup(basicButton, cityButton, cdcButton);
		buttonGroup.setMinCheckCount(1);
		buttonGroup.setMaxCheckCount(1);
		buttonGroup.setUncheckLast(true);
		Stack stack = new Stack();
		stack.setFillParent(true);
		basicTable = new Table(contagion.skin);
		basicTable.add(new Image(contagion.gameAtlas.createSprite("city", 0)));
		label = new Label("Cities become infected.", contagion.skin);
		label.setWrap(true);
		basicTable.add(label).expandX().fillX().row();
		basicTable.add();
		label = new Label("Clicking a city will asign a CDC and reduce the infection.", contagion.skin);
		label.setWrap(true);
		basicTable.add(label).expandX().fillX().row();
		basicTable.add(new Image(contagion.gameAtlas.createSprite("needle")));
		label = new Label("Pressing the needle button vaccinates the world. (Both on Android).", contagion.skin);
		label.setWrap(true);
		basicTable.add(label).expandX().fillX().row();
		basicTable.add(new Image(contagion.gameAtlas.createSprite("teamicons")));
		label = new Label("You have up to four teams to use.", contagion.skin);
		label.setWrap(true);
		basicTable.add(label).expandX().fillX().row();
		basicTable.add();
		label = new Label("Green: Ready. Red: Working. Yellow: Resting.", contagion.skin);
		label.setWrap(true);
		basicTable.add(label).expandX().fillX().row();
		basicTable.pack();
		cityTable = new Table(contagion.skin);
		cityTable.debug();
		cdcTable = new Table(contagion.skin);
		cdcTable.setFillParent(true);
		// stack.add(basicTable);
		mainCell = mainTable.add(basicTable).expand().fill().colspan(3);
		mainTable.pack();
		basicTable.pack();
		mainTable.setSize(700, 360);
		mainTable.setPosition(50, 40);
		buttonGroup.setChecked("Basics");
		stage.addActor(mainTable);

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

}
