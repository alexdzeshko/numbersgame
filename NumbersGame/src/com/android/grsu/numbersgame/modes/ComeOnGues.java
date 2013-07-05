package com.android.grsu.numbersgame.modes;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender.SendIntentException;
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
			OnFinishListener listener) {
		super(context, taskView, resultView, taskView, listener);
		mResources = mContext.getResources();
		mDrawables = new HashMap<Integer, Drawable>();
		mAnimals = new HashMap<Integer, String>();
		putDrawables();
		putAnimals();
	}

	private void putAnimals() {
		mAnimals.put(0, "Кот");
		mAnimals.put(1, "Верблюд");
		mAnimals.put(2, "Хомяк");
		mAnimals.put(3, "Божья коровка");
		mAnimals.put(4, "Заяц");
		mAnimals.put(5, "Собака");
		mAnimals.put(6, "Слон");
		mAnimals.put(7, "Бабочка");
		mAnimals.put(8, "Симпсоны");
		mAnimals.put(9, "Сокол");
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
			changeViewColor(mResultTextView, R.color.green);
			changeViewText(mResultTextView,
					"Congratulations! you guessed it.. Changing task");
			playSignal(SoundManager.WIN);
			mListener.finish();
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
		changeViewColor(mResultTextView, R.color.red);
		changeViewText(mResultTextView, "You Lose");
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

	@Override
	public void theTimeHasEnded() {
		// TODO Auto-generated method stub
		
	}
}
