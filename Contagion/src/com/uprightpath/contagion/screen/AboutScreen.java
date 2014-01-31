package com.uprightpath.contagion.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.uprightpath.contagion.Contagion;

public class AboutScreen extends GermBackScreen {
	private Table mainTable;

	public AboutScreen(Contagion contagion) {
		super(contagion);
		mainTable = new Window("Tutorial", contagion.skin);

		mainTable.setSize(700, 360);
		mainTable.setPosition(50, 40);
		stage.addActor(mainTable);
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

}
