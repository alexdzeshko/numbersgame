package com.android.grsu.numbersgame.modes;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.grsu.numbersgame.BuildConfig;
import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.IMode;
import com.android.grsu.numbersgame.sound.SoundManager;

public class GuessNumber extends IMode {

	private static final String LOG_TAG = GuessNumber.class.getSimpleName();
	private int mRightNumber, mAttempts;
	private Context mContext;
	private Random mRandom;
	private TextView mResultTextView, mTaskTextView;
	private OnFinishListener mCallback;

	public GuessNumber(Context context, TextView taskTextView,
			TextView resultTextView, OnFinishListener finishCallback) {
		mContext = context;
		mResultTextView = resultTextView;
		mTaskTextView = taskTextView;
		mRandom = new Random();
		mCallback = finishCallback;
		changeRightNumber();
	}

	public void gameOver() {
		changeViewColor(R.color.red);
		playSignal(SoundManager.LOSS);
		mCallback.finish();
	}

	public void makeGuess(int guessNumber) {
		if (guessNumber < 0 || guessNumber > 9) {
			changeViewText("This number does not exists in this game!");
			gameOver();
		}
		Integer signal = null;
		if (guessNumber > mRightNumber) {
			mAttempts++;
			signal = SoundManager.LESS;
			changeViewText("Less than " + guessNumber);
		}
		if (guessNumber < mRightNumber) {
			mAttempts++;
			signal = SoundManager.MORE;
			changeViewText("More than " + guessNumber);
		}
		if (guessNumber == mRightNumber) {
			changeViewText("Congratulations! you guessed it.. Changing number");
			changeViewColor(R.color.green);
			playSignal(SoundManager.WIN);
			reset();
			mCallback.finish();
			return;
		}
		if (mAttempts == 4) {
			gameOver();
		} else if (signal != null) {
			playSignal(signal);
		}
	}

	private void reset() {
		changeRightNumber();
		mAttempts = 0;
		mResultTextView.setText("");
	}

	private void changeRightNumber() {
		mRightNumber = mRandom.nextInt(9);
	}

	private void changeViewColor(int resColor) {
		mResultTextView.setBackgroundColor(mContext.getResources().getColor(
				resColor));
	}

	private void changeViewText(String text) {
		mResultTextView.setText(text);
	}

	@Override
	public void buttonPressed(int button) {
		makeGuess(button);
		if (BuildConfig.DEBUG) {
			Log.d(LOG_TAG, "buttonPressed: " + button);
		}
	}

	@Override
	public void startNewGame() {
		reset();
		mTaskTextView.setText("Guess Number");
		changeViewColor(R.color.white);

	}

}
