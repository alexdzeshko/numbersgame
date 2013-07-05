package com.android.grsu.numbersgame.modes.common;

import java.util.Random;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.format.Time;
import android.widget.TextView;

import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.sound.SoundManager;

public abstract class CommonMode {

	private Random mRandom;
	protected Context mContext;
	protected OnFinishListener mListener;
	protected TextView mTaskTextView, mResultTextView, mTextViewTimer;
	protected boolean isPlaying;

	private int mScore;

	private CountDownTimer timer;

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

	public int scorePlus() {
		return ++mScore;
	}

	public boolean highScore(){
		return mScore >= 10;
	}
	public void timerStart() {
		timer.start();
	}

	public void timerStop() {
		timer.cancel();
	}

	protected CommonMode(Context context, TextView taskView,
			TextView resultView, TextView timerT, OnFinishListener listener) {
		mContext = context;
		mTaskTextView = taskView;
		mResultTextView = resultView;
		mTextViewTimer = timerT;
		mRandom = new Random();
		mListener = listener;
		timer = new CountDownTimer(10000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				
				mTextViewTimer.setText(String.valueOf(millisUntilFinished/1000));

			}

			@Override
			public void onFinish() {
				theTimeHasEnded();

			}
		};
	}

	protected void changeViewColor(TextView view, int resColor) {
		view.setBackgroundColor(mContext.getResources().getColor(resColor));
	}

	protected void changeViewText(TextView view, String text) {
		view.setText(text);
	}

	protected int nextRandom() {
		return mRandom.nextInt(9);
	}

	protected boolean nextBoolean() {
		return mRandom.nextBoolean();
	}

	public void gameOver() {
		reset();
		playSignal(SoundManager.LOSS);
	}

	public abstract void buttonPressed(int button);
	
	public abstract void theTimeHasEnded();
	
	public abstract void startNewGame();

	public void reset(){
		mScore = 0;
		timer.cancel();
	}

}

