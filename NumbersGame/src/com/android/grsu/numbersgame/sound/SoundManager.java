package com.android.grsu.numbersgame.sound;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

	private MediaPlayer mPlayer;
	private Context mContext;
	private int mMusicId;

	public SoundManager(Context context, int id) {
		mContext = context;
		mMusicId = id;

	}

	public void playBackgroundMusic() {
		if (mPlayer == null) {
			mPlayer = MediaPlayer.create(mContext, mMusicId);
			mPlayer.setLooping(true);
		}
		mPlayer.start();
	}

	public void pauseBackgroundMusic() {
		if (mPlayer != null) {
			mPlayer.pause();
		} else
			throw new IllegalStateException(
					"Can be paused only after starting playback");
	}
	
	public void stopBackgroundMusic(){
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}else
			throw new IllegalStateException(
					"Can be stopped only after starting playback or paused");
	}

}
