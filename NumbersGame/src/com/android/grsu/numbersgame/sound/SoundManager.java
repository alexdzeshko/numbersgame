package com.android.grsu.numbersgame.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.android.grsu.numbersgame.R;

public class SoundManager {

	private MediaPlayer mPlayer;
	private Context mContext;
	private int mMusicId;

	public static final int MORE = R.raw.more;
	public static final int LESS = R.raw.less;
	public static final int WIN = R.raw.win;
	public static final int LOSS = R.raw.loss;

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

	public void stopBackgroundMusic() {
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		} else
			throw new IllegalStateException(
					"Can be stopped only after starting playback or paused");
	}

	public void playSignal(int id) {
		MediaPlayer mp = MediaPlayer.create(mContext, id);
		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (mp != null) {
					mp.release();
					mp = null;
				}
			}
		});
		mp.start();
	}
}
