package com.android.grsu.numbersgame.modes;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.CommonMode;

public class ComeOnGues extends CommonMode {

	private static ComeOnGues instance;

	private int mTaskNumber;
	private Map<Integer, Drawable> mDrawables;
	private Resources mResources;
	// if mTypeOfTask == true => task is number, else task is drawable
	private boolean mTypeOfTask;

	private ImageView mImageView;

	@SuppressLint("UseSparseArrays")
	protected ComeOnGues(Context context, TextView taskView,
			TextView resultView, ImageView imageView, OnFinishListener listener) {
		super(context, taskView, resultView, listener);
		mImageView = imageView;
		mResources = mContext.getResources();
		mDrawables = new HashMap<Integer, Drawable>();
		putDrawables();
	}

	public static ComeOnGues getInstance(Context context, TextView taskView,
			TextView resultView, ImageView imageView, OnFinishListener listener) {
		if (instance == null) {
			instance = new ComeOnGues(context, taskView, resultView, imageView,
					listener);
		}
		return instance;
	}

	private void putDrawables() {
		mDrawables.put(0, mResources.getDrawable(R.id.button0));
		mDrawables.put(1, mResources.getDrawable(R.id.button1));
		mDrawables.put(2, mResources.getDrawable(R.id.button2));
		mDrawables.put(3, mResources.getDrawable(R.id.button3));
		mDrawables.put(4, mResources.getDrawable(R.id.button4));
		mDrawables.put(5, mResources.getDrawable(R.id.button5));
		mDrawables.put(6, mResources.getDrawable(R.id.button6));
		mDrawables.put(7, mResources.getDrawable(R.id.button7));
		mDrawables.put(8, mResources.getDrawable(R.id.button8));
		mDrawables.put(9, mResources.getDrawable(R.id.button9));
	}

	private void makeGuess(int buttonPressed) {
		if (buttonPressed == mTaskNumber) {
			changeViewColor(mResultTextView, mResources.getColor(R.color.green));
			changeViewText(mResultTextView,
					"Congratulations! you guessed it.. Changing task");
		} else {
			gameOver();
		}
	}

	private void prepareTask() {
		mTypeOfTask = nextBoolean();
		mTaskNumber = nextRandom();
		changeViewText(mTaskTextView, String.valueOf(mTaskNumber));
		if (mTypeOfTask) {
			mImageView.setImageDrawable(mDrawables.get(mTaskNumber));
		} else {
			changeViewText(mTaskTextView, String.valueOf(mTaskNumber));
		}
	}

	@Override
	public void gameOver() {
		changeViewColor(mResultTextView, mResources.getColor(R.color.red));
		mListener.finish();
	}

	@Override
	public void reset() {
		mTaskNumber = -1;
		changeViewText(mTaskTextView, "");
		changeViewText(mResultTextView, "");
		changeViewColor(mResultTextView, R.color.white);
	}

	@Override
	public void buttonPressed(int button) {
		makeGuess(button);
	}

	@Override
	public void startNewGame() {
		reset();
		prepareTask();
	}
}
