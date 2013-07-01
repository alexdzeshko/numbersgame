package com.android.grsu.numbersgame.modes.common;

import java.util.Random;

import android.content.Context;
import android.widget.TextView;

import com.android.grsu.numbersgame.callbacks.FinishCallback;

public abstract class CommonMode implements IMode {

	private Random mRandom;
	protected Context mContext;
	protected FinishCallback mCallback;
	protected TextView mTaskTextView, mResultTextView;

	protected CommonMode(Context context, TextView tastView,
			TextView resultView, FinishCallback finishCallback) {
		mContext = context;
		mTaskTextView = tastView;
		mResultTextView = resultView;
		mRandom = new Random();
		mCallback = finishCallback;
	}

	protected int nextRandom() {
		return mRandom.nextInt(9);
	}

	protected boolean nextBoolean() {
		return mRandom.nextBoolean();
	}

	public abstract void gameOver();

	public abstract void reset();

}
