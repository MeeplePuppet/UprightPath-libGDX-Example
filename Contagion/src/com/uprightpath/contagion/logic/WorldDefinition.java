package com.uprightpath.contagion.logic;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;

public class WorldDefinition {
	public enum GameStyle {
		ALL, SPECIFIC
	}

	public String name;
	public GameStyle style;
	public String background;
	public String description;
	public Array<ModeDefinition> modes = new Array<ModeDefinition>();
	public Array<CityDefinition> cities = new Array<CityDefinition>();
	public Array<CityLinkDefinition> cityLinks = new Array<CityLinkDefinition>();

	public WorldDefinition(Element parent) {
		name = parent.getAttribute("name");
		style = GameStyle.valueOf(parent.getAttribute("style"));
		background = parent.getAttribute("background");
		description = parent.getAttribute("description");
		for (Element element : parent.getChildrenByName("mode")) {
			ModeDefinition mode = new ModeDefinition();
			mode.name = element.getAttribute("name");
			mode.chance = Float.parseFloat(element.getAttribute("chance"));
			mode.cureTime = element.getIntAttribute("curetime");
			mode.numCDC = element.getIntAttribute("numcdc");
			mode.rate = element.getIntAttribute("rate");
			modes.add(mode);
		}
		for (Element element : parent.getChildrenByName("city")) {
			CityDefinition city = new CityDefinition();
			city.id = element.getIntAttribute("id");
			city.x = Float.parseFloat(element.getAttribute("x")) - .5f;
			city.y = Float.parseFloat(element.getAttribute("y")) -.5f;
			city.coeff = Float.parseFloat(element.getAttribute("coeff"));
			cities.add(city);
			if (style == GameStyle.SPECIFIC) {
				city.required = Boolean.parseBoolean(element.getAttribute("req"));
			}
		}
		for (Element element : parent.getChildrenByName("citylink")) {
			CityLinkDefinition cityLink = new CityLinkDefinition();
			cityLink.first = element.getIntAttribute("first");
			cityLink.second = element.getIntAttribute("second");
			cityLinks.add(cityLink);
		}
	}

	public World createWorld(int modeId) {
		ModeDefinition mode = modes.get(modeId);
		World world = new World(this, mode.chance, mode.rate, mode.cureTime, mode.numCDC);
		for (CityDefinition city : cities) {
			world.addCity(new City(world, city.id, city.x, city.y, city.coeff));
		}
		for (CityLinkDefinition cityLink : cityLinks) {
			world.linkCity(cityLink.first, cityLink.second);
		}
		return world;
	}

	public class ModeDefinition {
		public String name;
		public float chance;
		public int rate;
		public int cureTime;
		public int numCDC;
	}

	public class CityDefinition {
		public float coeff;
		public int id;
		public float x;
		public float y;
		public float infectionCoeff;
		public boolean required = true;
	}

	public class CityLinkDefinition {
		public int first;
		public int second;
	}
}
