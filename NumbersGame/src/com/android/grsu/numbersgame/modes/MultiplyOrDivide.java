package com.android.grsu.numbersgame.modes;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.grsu.numbersgame.BuildConfig;
import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.CommonMode;

public class MultiplyOrDivide extends CommonMode {

	private static final String LOG_TAG = MultiplyOrDivide.class
			.getSimpleName();

	private int mFirstArithmeticMember;
	private int mSecondArithmeticMember;
	private int mRightAnswer;
	private int mCounter;
	private String mRightAnswerString;
	private boolean mOperation;

	private static MultiplyOrDivide instance;

	private MultiplyOrDivide(Context context, TextView taskTextView,
			TextView resultTextView, OnFinishListener finishCallback) {
		super(context, taskTextView, resultTextView, finishCallback);
	}

	public static MultiplyOrDivide getInstance(Context context,
			TextView taskTextView, TextView resultTextView,
			OnFinishListener finishCallback) {
		if (instance == null) {
			instance = new MultiplyOrDivide(context, taskTextView,
					resultTextView, finishCallback);
		}
		return instance;
	}

	private void makeGuess(int button) {
		int currentNumber = Integer
				.valueOf(mRightAnswerString.charAt(mCounter));
		if (currentNumber == button) {
			resumeOrFinish();
		} else {
			gameOver();
		}
	}

	private void resumeOrFinish() {
		if (mCounter == mRightAnswerString.length() - 1) {
			finish();
		} else {
			resume();
		}
	}

	private void finish() {
		changeViewText(mResultTextView,
				"Congratulations! you guessed it.. Changing task");
		changeViewColor(mResultTextView, R.color.green);
		mListener.finish();
		reset();
	}

	private void resume() {
		changeViewText(mResultTextView, "Waiting other number...");
		changeViewColor(mResultTextView, R.color.YellowGreen);
		mCounter++;
	}

	private void prepareTask() {
		mFirstArithmeticMember = nextRandom();
		mSecondArithmeticMember = nextRandom();
		mOperation = nextBoolean();
		if (mOperation) {
			mRightAnswer = mFirstArithmeticMember * mSecondArithmeticMember;
		} else {
			if (mFirstArithmeticMember > mSecondArithmeticMember) {
				mRightAnswer = mFirstArithmeticMember / mSecondArithmeticMember;
			} else {
				mRightAnswer = mSecondArithmeticMember / mFirstArithmeticMember;
			}
		}
		mRightAnswerString = String.valueOf(mRightAnswer);
		prepareTaskString();
	}

	private void prepareTaskString() {
		String operation = "";
		if (mOperation) {
			operation = " * ";
		} else {
			operation = " / ";
		}
		changeViewText(mTaskTextView, mFirstArithmeticMember + operation
				+ mSecondArithmeticMember);
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
		prepareTask();
	}

	@Override
	public void gameOver() {
		reset();
		changeViewColor(mResultTextView, R.color.red);
		mListener.finish();
	}

	@Override
	public void reset() {
		mCounter = 0;
	}

}
