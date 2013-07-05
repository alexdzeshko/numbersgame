package com.android.grsu.numbersgame.modes;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.widget.TextView;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.common.CommonMode;

public class RememberMore extends CommonMode {

	public RememberMore(Context context, TextView tastView,
			TextView resultView, TextView mTextViewTimer, TextView mTextViewScore, OnFinishListener finishCallback) {
		super(context, tastView, resultView, mTextViewTimer, mTextViewScore, finishCallback);
	}

	private Queue<Integer> mQueueNew, mQueueOld;
	private boolean noMistakes = false, isTyping = false;

	@Override
	public void buttonPressed(int button) {
		if (!isTyping) {
			mQueueOld = mQueueNew;
			mQueueNew = new LinkedList<Integer>();
		}
		if (mQueueOld.peek() == button) {
			mQueueNew.add(mQueueOld.poll());
			if (mQueueOld.size() != 0) {
				isTyping = true;
				mResultTextView.setText("You pressed " + button
						+ ". Press next number");
			} else {
				scorePlus();
				isTyping = false;
				showNext();
			}
		} else if (mQueueOld.peek() == null && noMistakes) {
			scorePlus();
			showNext();
		} else {
			noMistakes = false;
			gameOver();
		}
	}

	@Override
	public void startNewGame() {
		resetScore();
		noMistakes = true;
		mResultTextView.setText("");
		reset();
		showNext();
		changeViewColor(mResultTextView, R.color.white);

	}

	@Override
	public void reset() {
		mQueueNew = new LinkedList<Integer>();
		mQueueOld = new LinkedList<Integer>();
	}

	@Override
	public void gameOver() {
		super.gameOver();
		timerStop();
		reset();
		changeViewColor(mResultTextView, R.color.red);
		mListener.finish(mScore);
	}

	public void showNext() {
		int next = nextRandom();
		mQueueNew.add(next);
		mResultTextView.setText("Next number is " + next);
	}

	@Override
	public void theTimeHasEnded() {
		mListener.finish(mScore);
		
	}

	@Override
	public void prolongate() {
		// TODO Auto-generated method stub
		
	}

}
