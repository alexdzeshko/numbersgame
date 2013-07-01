package com.android.grsu.numbersgame.modes;

import java.util.Random;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.FinishCallback;

import android.content.Context;
import android.widget.TextView;

public class GuessNumber {

	private static volatile GuessNumber instance;

	private int mRightNumber;
	private Context mContext;
	private Random mRandom;
	private TextView mTextView;
	private int mAttempts;
	private FinishCallback mCallback;

	private GuessNumber(Context context, TextView textView,
			FinishCallback finishCallback) {
		mContext = context;
		mTextView = textView;
		mRandom = new Random();
		changeRightNumber();
	}

	/** Double Checked Locking Singleton & volatile */
	public static GuessNumber getInstance(Context context, TextView textView,
			FinishCallback finishCallback) {
		GuessNumber localInstance = instance;
		if (localInstance == null) {
			synchronized (GuessNumber.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new GuessNumber(context,
							textView, finishCallback);
				}
			}
		}
		return localInstance;
	}

	public void newGame() {
		reset();
		changeViewColor(R.color.white);
	}

	public void gameOver() {
		reset();
		changeViewColor(R.color.red);
		mCallback.finish();
	}

	public void makeGuess(int guessNumber) {
		if (guessNumber < 0 || guessNumber > 9) {
			changeViewText("This number does not exists in this game!");
			gameOver();
		}
		if (guessNumber > mRightNumber) {
			mAttempts++;
			changeViewText("Less");
		}
		if (guessNumber < mRightNumber) {
			mAttempts++;
			changeViewText("More");
		}
		if (guessNumber == mRightNumber) {
			changeViewText("Congratulations! you guessed it.. Changing number");
			changeViewColor(R.color.green);
			reset();
			mCallback.finish();
			return;
		}
		if (mAttempts == 4) {
			gameOver();
		}
	}

	private void reset() {
		changeRightNumber();
		mAttempts = 0;
		mRightNumber = -1;
	}

	private void changeRightNumber() {
		mRightNumber = mRandom.nextInt(9);
	}

	private void changeViewColor(int resColor) {
		mTextView
				.setBackgroundColor(mContext.getResources().getColor(resColor));
	}

	private void changeViewText(String text) {
		mTextView.setText(text);
	}

}
