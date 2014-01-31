package com.uprightpath.contagion.screen;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uprightpath.contagion.Contagion;
import com.uprightpath.contagion.logic.WorldDefinition;
import com.uprightpath.contagion.logic.WorldDefinition.ModeDefinition;

public class GameSelectScreen extends GermBackScreen {
	private Tree tree;
	private TextButton startButton;
	private Table descriptionTable;
	private Table table;
	private ScrollPane worldDescription;
	private Label worldName;
	private Label worldCDCTeam;
	private Label worldCDCSpeed;
	private Label worldInfectionRate;
	private Label worldMutateChance;
	private Label worldStory;
	private HashMap<Node, SelectedWorldDefinition> modeTable = new HashMap<Node, SelectedWorldDefinition>();

	public GameSelectScreen(Contagion contagion) {
		super(contagion);
		WorldDefinition worldDef;
		ModeDefinition mode;
		Node mainNode, childNode;
		table = new Window("World Select", contagion.skin);
		tree = new Tree(contagion.skin);
		tree.setMultiSelect(false);
		for (int i = 0; i < contagion.worldDefinitionManager.getNumberOfWorlds(); i++) {
			worldDef = contagion.worldDefinitionManager.getDefinition(i);
			mainNode = new Node(new Label(worldDef.name + "(" + worldDef.style.name() + ")", contagion.skin));
			for (int j = 0; j < worldDef.modes.size; j++) {
				mode = worldDef.modes.get(j);
				childNode = new Node(new Label(mode.name, contagion.skin));
				mainNode.add(childNode);
				modeTable.put(childNode, new SelectedWorldDefinition(worldDef, j));
			}
			tree.add(mainNode);
		}
		tree.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Tree tree = GameSelectScreen.this.tree;
				Node selected;
				if (tree.getSelection().size > 0) {
					selected = tree.getSelection().get(0);
					selected.setExpanded(true);
					if (selected.getChildren().size > 0) {
						tree.setSelection(selected.getChildren().get(0));
					}
					startButton.setDisabled(false);
					updateSelected();
				} else {
					startButton.setDisabled(true);
				}
			}
		});
		ScrollPane scrollPane = new ScrollPane(tree, contagion.skin);
		scrollPane.setFadeScrollBars(false);
		table.add(scrollPane).left().pad(2).minWidth(200).expandY().fillY();
		descriptionTable = new Table(contagion.skin);
		descriptionTable.add();
		descriptionTable.add();
		descriptionTable.add().fillX().expandX().row();
		descriptionTable.add("Name:").pad(2).left();
		worldName = new Label("", contagion.skin);
		descriptionTable.add(worldName).pad(2).row();
		descriptionTable.add("CDC Teams:").pad(2).left();
		worldCDCTeam = new Label("", contagion.skin);
		descriptionTable.add(worldCDCTeam).pad(2).row();
		descriptionTable.add("CDC Speed:").pad(2).left();
		worldCDCSpeed = new Label("", contagion.skin);
		descriptionTable.add(worldCDCSpeed).pad(2).row();
		descriptionTable.add("Rate:").pad(2).left();
		worldInfectionRate = new Label("", contagion.skin);
		descriptionTable.add(worldInfectionRate).pad(2).row();
		descriptionTable.add("Mutate:").pad(2).left();
		worldMutateChance = new Label("", contagion.skin);
		descriptionTable.add(worldMutateChance).pad(2).row();
		descriptionTable.row().pad(2).left();
		worldStory = new Label("", contagion.skin);
		worldStory.setWrap(true);
		descriptionTable.add(worldStory).pad(2).colspan(3).fill().expand();
		worldDescription = new ScrollPane(descriptionTable, contagion.skin);
		table.add(worldDescription).pad(2).top().left().expand().fill().row();

		startButton = new TextButton("Start Game", contagion.skin);
		startButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Tree tree = GameSelectScreen.this.tree;
				SelectedWorldDefinition swd = modeTable.get(tree.getSelection().get(0));
				if (swd != null) {
					GameSelectScreen.this.contagion.world = swd.worldDefintion.createWorld(swd.mode);
					GameSelectScreen.this.contagion.changeScreen(Contagion.ScreenType.GAME);
				}

			}
		});
		startButton.setDisabled(true);
		table.add(startButton).colspan(2).pad(2);
		table.pack();
		table.setSize(700, 360);
		table.setPosition(50, 40);
		stage.addActor(table);
	}

	protected void updateSelected() {
		SelectedWorldDefinition swd = modeTable.get(tree.getSelection().get(0));
		worldName.setText(swd.worldDefintion.name);
		worldCDCTeam.setText(swd.worldDefintion.modes.get(swd.mode).numCDC + "");
		worldCDCSpeed.setText("GTE " + swd.worldDefintion.modes.get(swd.mode).cureTime + " days");
		worldInfectionRate.setText(swd.worldDefintion.modes.get(swd.mode).rate + "");
		worldMutateChance.setText(swd.worldDefintion.modes.get(swd.mode).chance + "");
		worldStory.setText("Story: " + swd.worldDefintion.description);
		worldStory.setWrap(true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	public class SelectedWorldDefinition {
		WorldDefinition worldDefintion;
		int mode;

		public SelectedWorldDefinition(WorldDefinition worldDefinition, int mode) {
			this.worldDefintion = worldDefinition;
			this.mode = mode;
		}
	}
}
