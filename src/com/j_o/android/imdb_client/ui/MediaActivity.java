/*
 * Copyright 2013 Jose Ortega
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.j_o.android.imdb_client.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.aranda.android.imdb_client.R;
import com.j_o.android.imdb_client.model.Media;
import com.j_o.android.imdb_client.util.AppConstans;

public class MediaActivity extends ActionBarActivity {

	private TextView txtMediaName;
	private TextView txtMediaGenre;
	private TextView txtMediaActors;
	private TextView txtMediaDirector;
	private TextView txtMediaDuration;
	private TextView txtMediaPlot;

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		txtMediaName = (TextView) findViewById(R.id.txt_name_value);
		txtMediaGenre = (TextView) findViewById(R.id.txt_genre_value);
		txtMediaActors = (TextView) findViewById(R.id.txt_actors_value);
		txtMediaDirector = (TextView) findViewById(R.id.txt_director_value);
		txtMediaDuration = (TextView) findViewById(R.id.txt_duration_value);
		txtMediaPlot = (TextView) findViewById(R.id.txt_plot_value);

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		if (getIntent().getExtras() != null) {
			try {
				Media media = (Media) getIntent().getParcelableExtra(
						AppConstans.MEDIA);
				setTitle(media.getTitle());
				txtMediaName.setText(media.getTitle());
				txtMediaGenre.setText(media.getGenre());
				txtMediaActors.setText(media.getActors());
				txtMediaDirector.setText(media.getDirector());
				txtMediaDuration.setText(media.getDuraction());
				txtMediaPlot.setText(media.getPlot());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
