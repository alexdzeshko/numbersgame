package com.android.grsu.numbersgame.fragment;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.grsu.numbersgame.ContextHolder;
import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.ActivityCallback;
import com.android.grsu.numbersgame.callbacks.OnFinishListener;
import com.android.grsu.numbersgame.modes.ComeOnGues;
import com.android.grsu.numbersgame.modes.GuessNumber;
import com.android.grsu.numbersgame.modes.MultiplyOrDivide;
import com.android.grsu.numbersgame.modes.RememberMore;
import com.android.grsu.numbersgame.modes.SummOrSubtract;
import com.android.grsu.numbersgame.modes.common.CommonMode;
import com.android.grsu.numbersgame.sound.SoundManager;

public class GameFragment extends Fragment implements OnClickListener {

	public static final String MODE_NUMBER = "MODE_NUM";
	public static final String MODE = "MODE";

	private int mMode;
	private TextView mTextViewTask, mTextViewResult, mTextViewTimer,
			mTextViewScore;
	private CommonMode mGameManager;
	private ActivityCallback mActivityCallback;
	private OnFinishListener mFinishCallback;
	private SoundManager mSoundManager;
	private boolean mHardMode;
	private Button[] mButtons = new Button[10];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMode = getArguments().getInt(MODE_NUMBER);
		mFinishCallback = new OnFinishListener() {

			@Override
			public void finish(int score) {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						ContextHolder.getContext());
				dialogBuilder
						.setMessage("Your score is " + score + "!\n New game?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mGameManager.startNewGame();
									};

								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mActivityCallback.openDrawer(true);
									}
								}).show();
			}
		};
		mHardMode = getArguments().getBoolean(MODE);

	}

	@SuppressWarnings("deprecation")
	private void randomize() {
		if (mHardMode) {
			Random r = new Random();
			for (int i = 0; i < mButtons.length; i++) {
				Button buttonA = mButtons[r.nextInt(9)];
				int a = (Integer) buttonA.getTag();
				Drawable dra = buttonA.getBackground();

				buttonA.setTag(mButtons[i].getTag());
				buttonA.setBackgroundDrawable(mButtons[i].getBackground());
				mButtons[i].setTag(a);
				mButtons[i].setBackgroundDrawable(dra);
			}
		}

	}

	private void initMode() {
		switch (mMode) {
		case 0:
			mGameManager = new GuessNumber(getActivity(), mTextViewTask,
					mTextViewResult, mTextViewTimer, mTextViewScore,
					mFinishCallback);
			break;
		case 1:
			mGameManager = new RememberMore(getActivity(), mTextViewTask,
					mTextViewResult, mTextViewTimer, mTextViewScore,
					mFinishCallback);
			break;
		case 2:
			mGameManager = new MultiplyOrDivide(getActivity(), mTextViewTask,
					mTextViewResult, mTextViewTimer, mTextViewScore,
					mFinishCallback);
			break;
		case 3:
			mGameManager = new SummOrSubtract(getActivity(), mTextViewTask,
					mTextViewResult, mTextViewTimer, mTextViewScore,
					mFinishCallback);
			break;
		case 4:
			mGameManager = new ComeOnGues(getActivity(), mTextViewTask,
					mTextViewResult, mTextViewTimer, mTextViewScore,
					mFinishCallback);
			break;
		default:
			break;
		}
		mGameManager.setSoundManager(mSoundManager);
		mGameManager.startNewGame();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game, null);
		mTextViewTask = (TextView) view.findViewById(R.id.text_view_task);
		mTextViewResult = (TextView) view.findViewById(R.id.text_view_results);
		mTextViewTimer = (TextView) view.findViewById(R.id.timer);
		mTextViewScore = (TextView) view.findViewById(R.id.score);
		mButtons[1] = (Button) view.findViewById(R.id.button1);
		mButtons[2] = (Button) view.findViewById(R.id.button2);
		mButtons[3] = (Button) view.findViewById(R.id.button3);
		mButtons[4] = (Button) view.findViewById(R.id.button4);
		mButtons[5] = (Button) view.findViewById(R.id.button5);
		mButtons[6] = (Button) view.findViewById(R.id.button6);
		mButtons[7] = (Button) view.findViewById(R.id.button7);
		mButtons[8] = (Button) view.findViewById(R.id.button8);
		mButtons[9] = (Button) view.findViewById(R.id.button9);
		mButtons[0] = (Button) view.findViewById(R.id.button0);
		for (int i = 0; i < mButtons.length; i++) {
			mButtons[i].setOnClickListener(this);
			mButtons[i].setTag(i);
		}

		randomize();

		initMode();

		return view;
	}

	@Override
	public void onClick(View v) {
		mGameManager.buttonPressed((Integer) v.getTag());
		randomize();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);

	}

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof ActivityCallback))
			throw new IllegalArgumentException(
					"Activity must implements ActivityCallback");
		mActivityCallback = (ActivityCallback) activity;
		mSoundManager = mActivityCallback.getSoundManager();
		super.onAttach(activity);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mGameManager.reset();
	}

}
