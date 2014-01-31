package com.uprightpath.twentyeight.game;

import com.badlogic.gdx.utils.Array;

public class MineEventDispatcher implements MineEventListener {

	private Array<MineEventListener> listeners = new Array<MineEventListener>();

	public void addListener(MineEventListener listener) {
		if (!listeners.contains(listener, true)) {
			listeners.add(listener);
		}
	}

	public void removeListener(MineEventListener listener) {
		listeners.removeValue(listener, true);
	}

	@Override
	public void update() {
		for (MineEventListener listener : listeners) {
			listener.update();
		}
	}

	@Override
	public void ragFling() {
		for (MineEventListener listener : listeners) {
			listener.ragFling();
		}
	}

	@Override
	public void ragBurnt() {
		for (MineEventListener listener : listeners) {
			listener.ragBurnt();
		}
	}

	@Override
	public void candle() {
		for (MineEventListener listener : listeners) {
			listener.candle();
		}
	}

	@Override
	public void ignites() {
		for (MineEventListener listener : listeners) {
			listener.ignites();
		}
	}

	@Override
	public void burnedOut() {
		for (MineEventListener listener : listeners) {
			listener.burnedOut();
		}
	}

	@Override
	public void playerBurned() {
		for (MineEventListener listener : listeners) {
			listener.playerBurned();
		}
	}
}
