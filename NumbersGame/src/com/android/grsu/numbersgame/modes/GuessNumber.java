package com.android.grsu.numbersgame.modes;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.grsu.numbersgame.BuildConfig;
import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.CommonMode;
import com.android.grsu.numbersgame.sound.SoundManager;

public class GuessNumber extends CommonMode {

	private static final String LOG_TAG = GuessNumber.class.getSimpleName();
	private int mRightNumber, mAttempts;

	public GuessNumber(Context context, TextView taskView, TextView resultView,
			TextView mTextViewTimer, OnFinishListener listener) {
		super(context, taskView, resultView, mTextViewTimer, listener);
		changeRightNumber();
		startNewGame();
	}

	@Override
	public void gameOver() {

		changeViewColor(R.color.red);
		mListener.finish();
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
			mListener.finish();
			return;
		}
		if (mAttempts == 4) {
			gameOver();
		} else if (signal != null) {
			playSignal(signal);
		}
	}

	private void changeRightNumber() {
		mRightNumber = nextRandom();
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
		timerStart();
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
		timerStart();
	}

	@Override
	public void theTimeHasEnded() {
		gameOver();

	}

	@Override
	public void reset() {
		timerStop();
		changeRightNumber();
		mAttempts = 0;
		mResultTextView.setText("");
	}

}
