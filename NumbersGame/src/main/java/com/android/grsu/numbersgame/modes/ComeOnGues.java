package com.android.grsu.numbersgame.modes;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.CommonMode;
import com.android.grsu.numbersgame.sound.SoundManager;

public class ComeOnGues extends CommonMode {

	private int mTaskNumber;
	private Map<Integer, Drawable> mDrawables;
	private Map<Integer, String> mAnimals;
	private Resources mResources;
	// if mTypeOfTask == true => task is number, else task is drawable
	private boolean mTypeOfTask;

	@SuppressLint("UseSparseArrays")
	public ComeOnGues(Context context, TextView taskView, TextView resultView,
			TextView mTextViewTimer, TextView mTextViewScore,
			OnFinishListener listener) {
		super(context, taskView, resultView, mTextViewTimer, mTextViewScore, listener);
		mResources = mContext.getResources();
		mDrawables = new HashMap<Integer, Drawable>();
		mAnimals = new HashMap<Integer, String>();
		putDrawables();
		putAnimals();
	}

	private void putAnimals() {
		mAnimals.put(0, "���");
		mAnimals.put(1, "�������");
		mAnimals.put(2, "�����");
		mAnimals.put(3, "����� �������");
		mAnimals.put(4, "����");
		mAnimals.put(5, "������");
		mAnimals.put(6, "����");
		mAnimals.put(7, "�������");
		mAnimals.put(8, "��������");
		mAnimals.put(9, "�����");
	}

	private void putDrawables() {
		mDrawables.put(0, mResources.getDrawable(R.drawable.b0));
		mDrawables.put(1, mResources.getDrawable(R.drawable.b1));
		mDrawables.put(2, mResources.getDrawable(R.drawable.b2));
		mDrawables.put(3, mResources.getDrawable(R.drawable.b3));
		mDrawables.put(4, mResources.getDrawable(R.drawable.b4));
		mDrawables.put(5, mResources.getDrawable(R.drawable.b5));
		mDrawables.put(6, mResources.getDrawable(R.drawable.b6));
		mDrawables.put(7, mResources.getDrawable(R.drawable.b7));
		mDrawables.put(8, mResources.getDrawable(R.drawable.b8));
		mDrawables.put(9, mResources.getDrawable(R.drawable.b9));
	}

	private void makeGuess(int buttonPressed) {
		if (buttonPressed == mTaskNumber) {
			scorePlus();
			if (!highScore()) {
				prolongate();
			} else {
				timerStop();
				changeViewColor(mResultTextView, R.color.green);
				changeViewText(mResultTextView,
						"Congratulations!");
				playSignal(SoundManager.WIN);
				mListener.finish(mScore);
			}
		} else {
			gameOver();
		}
	}

	private void prepareTask() {
		mTypeOfTask = nextBoolean();
		mTaskNumber = nextRandom();
		changeViewText(mTaskTextView, String.valueOf(mTaskNumber));
		if (mTypeOfTask) {
			changeViewText(mTaskTextView, String.valueOf(mTaskNumber));
		} else {
			changeViewText(mTaskTextView, mAnimals.get(mTaskNumber));
		}
	}

	@Override
	public void gameOver() {
		super.gameOver();
		timerStop();
		changeViewColor(mResultTextView, R.color.red);
		changeViewText(mResultTextView, "You Lose");
		mListener.finish(mScore);
	}

	@Override
	public void reset() {
		resetScore();
		timerStop();
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
		timerStart();
	}

	@Override
	public void theTimeHasEnded() {
		gameOver();

	}

	@Override
	public void prolongate() {
		timerStop();
		prepareTask();
		timerStart();

	}
}
