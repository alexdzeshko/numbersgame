package com.android.grsu.numbersgame.modes;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.FinishCallback;

public class RememberMore implements IMode {

	private Queue<Integer> mLine;
	private Context mContext;
	private Random mRandom;
	private TextView mTextView;
	private FinishCallback mCallback;

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
			changeViewText("Less than " + guessNumber);
		}
		if (guessNumber < mRightNumber) {
			mAttempts++;
			changeViewText("More than " + guessNumber);
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
		mLine = new LinkedList<Integer>();
		changeRightNumber();
		mAttempts = 0;

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

	@Override
	public void buttonPressed(int button) {
		makeGuess(button);

	}

	@Override
	public void startNewGame() {
		mTextView.setText("");
		reset();
		int next = mRandom.nextInt(9);
		mLine.add(next);
		mTextView.setText("Next number is "+next);
		changeViewColor(R.color.white);

	}


}
