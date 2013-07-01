package com.android.grsu.numbersgame.modes;

import java.util.Random;

import android.content.Context;
import android.widget.Toast;

public class GuessNumber {

	private static volatile GuessNumber instance;

	private int mRightNumber;
	private Context mContext;
	private Random mRandom;

	private GuessNumber(Context context) {
		mContext = context;
		mRandom = new Random();
		changeRightNumber();
	}

	/** Double Checked Locking Singleton & volatile */
	public static GuessNumber getInstance(Context context) {
		GuessNumber localInstance = instance;
		if (localInstance == null) {
			synchronized (GuessNumber.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new GuessNumber(context);
				}
			}
		}
		return localInstance;
	}

	public void newGame() {
		mRightNumber = -1;
		changeRightNumber();
	}

	public void makeGuess(int guessNumber) {
		if (guessNumber < 0 || guessNumber > 9) {
			showToast("This number does not exists in this game!");
		}
		if (guessNumber > mRightNumber) {
			showToast("Less");
		}
		if (guessNumber < mRightNumber) {
			showToast("More");
		}
		if (guessNumber == mRightNumber) {
			showToast("Congratulations! you guessed it.. Changing number");
			newGame();
		}
	}

	private void changeRightNumber() {
		mRightNumber = mRandom.nextInt(9);
	}

	private void showToast(String message) {
		Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
	}
}
