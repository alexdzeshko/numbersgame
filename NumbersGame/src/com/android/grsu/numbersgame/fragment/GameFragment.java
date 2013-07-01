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

public class GameFragment extends Fragment implements OnClickListener {

	public static final String MODE_NUMBER = "MODE_NUM";
	
	private static final int BUTTON_1 = R.id.button1;
	private static final int BUTTON_2 = R.id.button2;
	private static final int BUTTON_3 = R.id.button3;
	private static final int BUTTON_4 = R.id.button4;
	private static final int BUTTON_5 = R.id.button5;
	private static final int BUTTON_6 = R.id.button6;
	
	private int mMode;
	private TextView mTextViewTask, mTextViewResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMode = getArguments().getInt(MODE_NUMBER);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game, null);
		mTextViewTask = (TextView) view.findViewById(R.id.text_view_task);
		mTextViewResult = (TextView) view.findViewById(R.id.text_view_results);
		Button button1 = (Button) view.findViewById(BUTTON_1);
		Button button2 = (Button) view.findViewById(BUTTON_2);
		Button button3 = (Button) view.findViewById(BUTTON_3);
		Button button4 = (Button) view.findViewById(BUTTON_4);
		Button button5 = (Button) view.findViewById(BUTTON_5);
		Button button6 = (Button) view.findViewById(BUTTON_6);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case BUTTON_1:
			
			break;

		default:
			break;
		}
		
	}

}
