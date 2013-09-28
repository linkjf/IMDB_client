package com.aranda.android.imdb_client.ui;

import com.aranda.android.imdb_client.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MediaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
