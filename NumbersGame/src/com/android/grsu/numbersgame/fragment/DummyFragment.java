package com.android.grsu.numbersgame.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.grsu.numbersgame.R;
import com.android.grsu.numbersgame.callbacks.ActivityCallback;

public class DummyFragment extends Fragment {

	private ActivityCallback mActivityCallback;

	@Override
	public void onAttach(Activity activity) {
		if (!(activity instanceof ActivityCallback))
			throw new IllegalArgumentException(
					"Activity must implements ActivityCallback");
		mActivityCallback = (ActivityCallback) activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dummy, null);
		Button b = (Button) view.findViewById(R.id.button_choose);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mActivityCallback.openDrawer(true);
				
			}
		});
		return view;
	}
	
}
