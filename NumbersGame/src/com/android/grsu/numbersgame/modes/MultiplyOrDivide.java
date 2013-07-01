package com.android.grsu.numbersgame.modes;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.grsu.numbersgame.BuildConfig;
import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.FinishCallback;

public class MultiplyOrDivide implements IMode {

	private static final String LOG_TAG = MultiplyOrDivide.class
			.getSimpleName();

	private Random mRandom;
	private Context mContext;
	private FinishCallback mCallback;
	private TextView mTaskTextView, mResultTextView;
	private int mFirstArithmeticMember;
	private int mSecondArithmeticMember;
	private int mRightAnswer;
	private int mUserAnswer;
	private int mCounter;
	private String mRightAnswerString;
	private boolean mOperation;

	private static MultiplyOrDivide instance;

	private MultiplyOrDivide(Context context, TextView textView,
			FinishCallback finishCallback) {
		mContext = context;
		mCallback = finishCallback;
	}

	public static MultiplyOrDivide getInstance(Context context,
			TextView textView, FinishCallback finishCallback) {
		if (instance == null) {
			instance = new MultiplyOrDivide(context, textView, finishCallback);
		}
		return instance;
	}

	public void makeGuess(int button) {
		int currentNumber = Integer
				.valueOf(mRightAnswerString.charAt(mCounter));
		if (currentNumber == button) {
			resumeOrFinish();
		} else {
			gameOver();
		}
	}

	public void gameOver() {
		reset();
		// TODO
	}

	private void resumeOrFinish() {
		if (mCounter == mRightAnswerString.length() - 1) {
			changeViewText(mResultTextView,
					"Congratulations! you guessed it.. Changing task");
			changeViewColor(mResultTextView, R.color.green);
			mCallback.finish();
			reset();
		} else {
			changeViewText(mResultTextView, "Waiting other number...");
			changeViewColor(mResultTextView, R.color.YellowGreen);
			mCounter++;
		}
	}

	private void prepareTask() {
		mFirstArithmeticMember = mRandom.nextInt(10);
		mSecondArithmeticMember = mRandom.nextInt(10);
		mOperation = mRandom.nextBoolean();
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
	}

	private void reset() {
		mCounter = 0;
	}

	private void changeViewColor(TextView view, int resColor) {
		view.setBackgroundColor(mContext.getResources().getColor(resColor));
	}

	private void changeViewText(TextView view, String text) {
		view.setText(text);
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
		prepareTask();
		prepareTaskString();
	}

}
