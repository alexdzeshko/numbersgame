package com.android.grsu.numbersgame.modes.common;

import com.android.grsu.numbersgame.sound.SoundManager;

public abstract class IMode {

	private SoundManager mSoundManager;
	public void setSoundManager(SoundManager manager) {
		mSoundManager = manager;
	}

	public void playSignal(int id) {
		if (mSoundManager != null) {
			mSoundManager.playSignal(id);
		} else
			throw new IllegalStateException(
					"SoundManager is not setted or setted to null");
	}
	
	public abstract void buttonPressed(int button);
	public abstract void startNewGame();

}
