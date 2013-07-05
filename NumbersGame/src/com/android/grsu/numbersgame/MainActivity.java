package com.android.grsu.numbersgame;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.grsu.numbersgame.callbacks.ActivityCallback;
import com.android.grsu.numbersgame.fragment.GameFragment;
import com.android.grsu.numbersgame.sound.SoundManager;

public class MainActivity extends FragmentActivity implements ActivityCallback {

	private String[] mModeTitles;
	private ListView mDrawerList;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private SoundManager mSoundManager;

	private boolean mHardMode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ContextHolder.setContext(this);
		mModeTitles = getResources().getStringArray(R.array.modes_array);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.adapter_drawer, mModeTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		//selectItem(0);
		mDrawerLayout.openDrawer(mDrawerList);
		mSoundManager = new SoundManager(this, R.raw.fon);
	}

	@Override
	public void onResume() {
		super.onResume();
		mSoundManager.playBackgroundMusic();
	}

	@Override
	public void onPause() {
		super.onPause();
		mSoundManager.pauseBackgroundMusic();
	}

	@Override
	public void onStop() {
		super.onStop();
		mSoundManager.stopBackgroundMusic();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.easy:
			mHardMode = false;
			break;
		case R.id.hard:
			mHardMode = true;
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		Fragment fragment = new GameFragment();
		Bundle args = new Bundle();
		args.putInt(GameFragment.MODE_NUMBER, position);
		args.putBoolean(GameFragment.MODE, mHardMode);
		fragment.setArguments(args);

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			setTitle(mModeTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public void openDrawer(boolean open) {
		if (open)
			mDrawerLayout.openDrawer(mDrawerList);
		else
			mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public SoundManager getSoundManager() {
		return mSoundManager;
	}
}
