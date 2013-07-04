package com.android.grsu.numbersgame.modes.common;

import java.util.Random;

import android.content.Context;
import android.widget.TextView;

import com.android.grsu.numbersgame.callbacks.OnFinishListener;

public abstract class CommonMode extends IMode {

	private Random mRandom;
	protected Context mContext;
	protected OnFinishListener mListener;
	protected TextView mTaskTextView, mResultTextView;

	protected CommonMode(Context context, TextView taskView,
			TextView resultView, OnFinishListener listener) {
		mContext = context;
		mTaskTextView = taskView;
		mResultTextView = resultView;
		mRandom = new Random();
		mListener = listener;
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

	public abstract void gameOver();

	public abstract void reset();

}
