package com.android.grsu.numbersgame.modes;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.grsu.numbersgame.BuildConfig;
import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.CommonMode;
import com.android.grsu.numbersgame.sound.SoundManager;

public class MultiplyOrDivide extends CommonMode {

	private static final String LOG_TAG = MultiplyOrDivide.class
			.getSimpleName();

	private int mFirstArithmeticMember;
	private int mSecondArithmeticMember;
	private int mRightAnswer;
	private int mCounter;
	private boolean mOperation;

	public MultiplyOrDivide(Context context, TextView taskTextView,
			TextView resultTextView, TextView mTextViewTimer, OnFinishListener finishCallback) {
		super(context, taskTextView, resultTextView, mTextViewTimer, finishCallback);
	}

	private void makeGuess(int button) {
		if (mRightAnswer < 10) {
			if (mRightAnswer == button) {
				finish();
			} else {
				gameOver();
			}
		} else {
			if (mCounter == 0) {
				if (mRightAnswer / 10 == button) {
					resume();
					return;
				} else {
					gameOver();
					return;
				}
			}
			if (mCounter == 1) {
				if (mRightAnswer % 10 == button) {
					finish();
					return;
				} else {
					gameOver();
					return;
				}
			}
		}
	}

	private void finish() {
		changeViewText(mResultTextView,
				"Congratulations! you guessed it.. Changing task");
		changeViewColor(mResultTextView, R.color.green);
		playSignal(SoundManager.WIN);
		mListener.finish();
	}

	private void resume() {
		changeViewText(mResultTextView, "Waiting other number...");
		changeViewColor(mResultTextView, R.color.YellowGreen);
		mCounter++;
	}

	private void prepareTask() {
		mOperation = nextBoolean();
		if (mOperation) {
			mFirstArithmeticMember = nextRandom();
			mSecondArithmeticMember = nextRandom();
			mRightAnswer = mFirstArithmeticMember * mSecondArithmeticMember;
		} else {
			while (mSecondArithmeticMember == 0) {
				mSecondArithmeticMember = nextRandom();
			}
			while (mRightAnswer == 0) {
				mRightAnswer = nextRandom();
			}
			mFirstArithmeticMember = mRightAnswer * mSecondArithmeticMember;
		}
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
		super.gameOver();
		changeViewColor(mResultTextView, R.color.red);
		mListener.finish();
	}

	@Override
	public void reset() {
		mCounter = 0;
		mRightAnswer = 0;
		mSecondArithmeticMember = 0;
		mFirstArithmeticMember = 0;
		changeViewText(mTaskTextView, "");
		changeViewText(mResultTextView, "");
		changeViewColor(mResultTextView, R.color.white);
	}

	@Override
	public void theTimeHasEnded() {
		// TODO Auto-generated method stub
		
	}

}
