package com.uprightpath.twentyeight;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager {
	private ObjectMap<String, Array<Sound>> soundByName = new ObjectMap<String, Array<Sound>>();
	public boolean mute = false;
	public float sound = .5f;

	public SoundManager() {
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public void playSound(String string) {
		if (!mute) {
			soundByName.get(string).get(MathUtils.random(2)).play(sound);
		}
	}

	public void addSound(String string, Sound sound) {
		if (soundByName.get(string) == null) {
			soundByName.put(string, new Array<Sound>());
		}
		soundByName.get(string).add(sound);
	}

	public void dispose() {
		for (Array<Sound> soundArray : soundByName.values()) {
			for (Sound sound : soundArray) {
				sound.dispose();
			}
		}
	}
}
