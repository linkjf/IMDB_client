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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.aranda.android.imdb_client.R;
import com.j_o.android.imdb_client.util.AppConstans;
import com.j_o.android.imdb_client.util.ConsumerWebService;

public class MediaActivity extends ActionBarActivity {

	private TextView txtMediaName;
	private TextView txtMediaGenre;
	private TextView txtMediaActors;
	private TextView txtMediaDirector;
	private TextView txtMediaDuration;
	private TextView txtMediaPlot;

	private ActionBar actionBar;
	private JSONObject jsonObject;

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

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		if (getIntent().getExtras() != null) {
			try {

				jsonObject = new JSONObject(getIntent().getExtras().getString(AppConstans.JSON_DATA_MAP));
				setTitle(jsonObject.getString("title"));

				new AskForMediaAsyncTaks().execute(jsonObject.getString("id"));
			}
			catch (Exception e) {
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

	private class AskForMediaAsyncTaks extends AsyncTask<String, Void, Boolean> {

		private JSONObject jsonObject;

		@Override
		protected Boolean doInBackground(String... params) {
			Log.d("lol", "lol");
			// Set rquest Data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("i", params[0]));
			try {
				this.jsonObject = ConsumerWebService.makeHttpRequestJSONObject(AppConstans.URL_BASE_WEB_SERVICE, "GET", nameValuePairs);

			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean isError) {
			super.onPostExecute(isError);

			if (this.jsonObject != null) {
				try {
					txtMediaName.setText(jsonObject.getString("Title"));
					txtMediaGenre.setText(jsonObject.getString("Genre"));
					txtMediaActors.setText(jsonObject.getString("Actors"));
					txtMediaDirector.setText(jsonObject.getString("Director"));
					txtMediaDuration.setText(jsonObject.getString("Runtime"));
					txtMediaPlot.setText(jsonObject.getString("Plot"));

				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

}
