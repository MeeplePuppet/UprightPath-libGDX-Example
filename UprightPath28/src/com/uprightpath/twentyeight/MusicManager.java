package com.uprightpath.twentyeight;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ObjectMap;

public class MusicManager {
	private ObjectMap<String, Music> soundByName = new ObjectMap<String, Music>();
	public boolean mute = false;
	public float sound = .5f;
	public Music currentMusic;

	public MusicManager() {
	}

	public void playSound(String string) {
		if (currentMusic != null) {
			currentMusic.stop();
		}
		currentMusic = soundByName.get(string);
		if (!mute) {
			currentMusic.play();
			currentMusic.setVolume(sound);
			currentMusic.setLooping(true);
		}
	}

	public void setSound(float volume) {
		if (currentMusic != null) {
			currentMusic.setVolume(volume);
		}
	}

	public void setMute(boolean mute) {
		this.mute = mute;
		if (currentMusic != null) {
			currentMusic.stop();
		}
	}

	public void addSound(String string, Music sound) {
		if (soundByName.get(string) != null) {
			soundByName.get(string).dispose();
		}
		soundByName.put(string, sound);
	}

	public void dispose() {
		for (Music sound : soundByName.values()) {
			sound.dispose();
		}
	}
}
