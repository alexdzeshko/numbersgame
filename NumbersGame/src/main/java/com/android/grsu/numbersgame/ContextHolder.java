package com.android.grsu.numbersgame;

import android.content.Context;

public class ContextHolder {

	private static Context mContext;

	private ContextHolder() {
	}

	public static void setContext(Context context) {
		mContext = context;
	}

	public static Context getContext() {
		return mContext;
	}
}
