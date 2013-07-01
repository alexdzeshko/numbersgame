package com.android.grsu.numbersgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.FinishCallback;
import com.android.grsu.numbersgame.modes.GuessNumber;
import com.android.grsu.numbersgame.modes.IMode;

public class GameFragment extends Fragment implements OnClickListener {

	public static final String MODE_NUMBER = "MODE_NUM";

	private int mMode;
	private TextView mTextViewTask, mTextViewResult;
	private IMode mGameManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMode = getArguments().getInt(MODE_NUMBER);
	}

	private void initMode() {
		switch (mMode) {
		case 0:
			mGameManager = GuessNumber.getInstance(getActivity(),
					mTextViewResult, new FinishCallback() {

						@Override
						public void finish() {
							// TODO Auto-generated method stub

						}
					});
			break;

		default:
			break;
		}

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

}
