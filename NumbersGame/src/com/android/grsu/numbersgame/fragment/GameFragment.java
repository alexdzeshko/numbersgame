package com.android.grsu.numbersgame.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.ActivityCallback;
import com.android.grsu.numbersgame.callbacks.FinishCallback;
import com.android.grsu.numbersgame.modes.GuessNumber;
import com.android.grsu.numbersgame.modes.RememberMore;
import com.android.grsu.numbersgame.modes.common.IMode;
import com.android.grsu.numbersgame.sound.SoundManager;

public class GameFragment extends Fragment implements OnClickListener {

	public static final String MODE_NUMBER = "MODE_NUM";

	private int mMode;
	private TextView mTextViewTask, mTextViewResult;
	private IMode mGameManager;
	private ActivityCallback mActivityCallback;
	private FinishCallback mFinishCallback;
	private SoundManager mSoundManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMode = getArguments().getInt(MODE_NUMBER);
		mFinishCallback = new FinishCallback() {

			@Override
			public void finish() {
				mGameManager.startNewGame();
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						getActivity());
				dialogBuilder
						.setMessage("New game?")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

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

	}

	private void initMode() {
		switch (mMode) {
		case 0:
			mGameManager = new GuessNumber(getActivity(), mTextViewResult,
					mFinishCallback, mSoundManager);
			break;
		case 1:
			mGameManager = new RememberMore(getActivity(), mTextViewTask,
					mTextViewResult, mFinishCallback);
		default:
			break;
		}

		mGameManager.startNewGame();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game, null);
		mTextViewTask = (TextView) view.findViewById(R.id.text_view_task);
		mTextViewResult = (TextView) view.findViewById(R.id.text_view_results);
		Button button1 = (Button) view.findViewById(R.id.button1);
		Button button2 = (Button) view.findViewById(R.id.button2);
		Button button3 = (Button) view.findViewById(R.id.button3);
		Button button4 = (Button) view.findViewById(R.id.button4);
		Button button5 = (Button) view.findViewById(R.id.button5);
		Button button6 = (Button) view.findViewById(R.id.button6);
		Button button7 = (Button) view.findViewById(R.id.button7);
		Button button8 = (Button) view.findViewById(R.id.button8);
		Button button9 = (Button) view.findViewById(R.id.button9);
		Button button0 = (Button) view.findViewById(R.id.button0);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		button7.setOnClickListener(this);
		button8.setOnClickListener(this);
		button9.setOnClickListener(this);
		button0.setOnClickListener(this);

		initMode();

		return view;
	}

	@Override
	public void onClick(View v) {
		mGameManager.buttonPressed(Integer.valueOf(((Button) v).getText()
				.toString()));
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

}
