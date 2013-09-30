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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aranda.android.imdb_client.R;
import com.j_o.android.imdb_client.util.AppConstans;
import com.j_o.android.imdb_client.util.ConsumerWebService;

public class MainActivity extends Activity {

	private GridView mediaGrid;
	private EditText editTxMediaSearch;
	private JSONArray mediaArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mediaArray = new JSONArray();
		mediaGrid = (GridView) findViewById(R.id.media_grid_view);
		editTxMediaSearch = (EditText) findViewById(R.id.edit_search_media);
		InputFilter filter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

				if (source instanceof SpannableStringBuilder) {
					SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder) source;
					for (int i = end - 1; i >= start; i--) {
						char currentChar = source.charAt(i);
						if (!Character.isLetterOrDigit(currentChar) && !Character.isSpaceChar(currentChar)) {
							sourceAsSpannableBuilder.delete(i, i + 1);
						}
					}
					return source;
				}
				else {
					StringBuilder filteredStringBuilder = new StringBuilder();
					for (int i = 0; i < end; i++) {
						char currentChar = source.charAt(i);
						if (Character.isLetterOrDigit(currentChar) || Character.isSpaceChar(currentChar)) {
							filteredStringBuilder.append(currentChar);
						}
					}
					return filteredStringBuilder.toString();
				}
			}
		};
		editTxMediaSearch.setFilters(new InputFilter[] { filter });

		editTxMediaSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					new AskForMediaAsyncTaks().execute(editTxMediaSearch.getText().toString());
					return true;
				}
				return false;
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MediaAdapter extends BaseAdapter implements OnItemClickListener {

		private LayoutInflater mInflater;

		public MediaAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return mediaArray.length();
		}

		@Override
		public Object getItem(int position) {
			Object object = null;
			try {
				object = mediaArray.get(position);
			}
			catch (JSONException e) {

				e.printStackTrace();
			}
			return object;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.d("get view", "view");

			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.media_item, parent, false);
				holder = new ViewHolder();
				holder.mediaImage = (ImageView) convertView.findViewById(R.id.image_media_poster);
				holder.mediaTitle = (TextView) convertView.findViewById(R.id.txt_media_title);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			try {
				JSONObject mediaObject = mediaArray.getJSONObject(position);
				holder.mediaTitle.setText(mediaObject.get("title").toString());

				new DownloadImageTask(holder.mediaImage).execute(mediaObject.getString("id"));

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(MainActivity.this, MediaActivity.class);
			try {

				intent.putExtra(AppConstans.JSON_DATA_MAP, mediaArray.getJSONObject(position).toString());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			startActivity(intent);
		}
	}

	static class ViewHolder {
		ImageView mediaImage;
		TextView mediaTitle;
	}

	private class AskForMediaAsyncTaks extends AsyncTask<String, Void, Boolean> {

		private ProgressDialog mProgressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setMessage(getString(R.string.lab_loking_for_media));
			mProgressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			Log.d("lol", "lol");
			// Set rquest Data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("json", "" + 1));
			nameValuePairs.add(new BasicNameValuePair("tt", "" + 1));
			nameValuePairs.add(new BasicNameValuePair("q", params[0]));
			try {
				JSONObject responseOjbect = ConsumerWebService.makeHttpRequestJSONObject(AppConstans.URL_BASE_WEB_SERVICE_IMDB, "GET", nameValuePairs);

				if (!responseOjbect.isNull("title_substring"))

					mediaArray = responseOjbect.getJSONArray("title_substring");

				else if (!responseOjbect.isNull("title_approx"))

					mediaArray = responseOjbect.getJSONArray("title_approx");

				else if (!responseOjbect.isNull("title_exact"))

					mediaArray = responseOjbect.getJSONArray("title_exact");

				else
					mediaArray = new JSONArray();
			}
			catch (Exception e) {

				e.printStackTrace();
				mediaArray = new JSONArray();

			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean isError) {
			super.onPostExecute(isError);
			mProgressDialog.dismiss();
			if (mediaGrid.getAdapter() != null) {
				((BaseAdapter) mediaGrid.getAdapter()).notifyDataSetChanged();
				if (mediaArray.length() == 0) Toast.makeText(getApplicationContext(), R.string.lab_no_media_found, Toast.LENGTH_SHORT).show();
			}
			else {

				MediaAdapter adapter = new MediaAdapter();
				mediaGrid.setAdapter(adapter);
				mediaGrid.setOnItemClickListener(adapter);
			}
		}

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			bmImage.setImageBitmap(null);
		}

		protected Bitmap doInBackground(String... movieID) {
			String id = movieID[0];
			Bitmap mIcon11 = null;
			try {

				try {

					// Set rquest Data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("i", id));

					JSONObject mediaObject = ConsumerWebService.makeHttpRequestJSONObject(AppConstans.URL_BASE_WEB_SERVICE, "GET", nameValuePairs);

					InputStream in = new java.net.URL(mediaObject.getString("Poster")).openStream();
					mIcon11 = BitmapFactory.decodeStream(in);
				}
				catch (Exception e) {
					Log.e("Error", e.getMessage());
					e.printStackTrace();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
			Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_animation_layout);
			// Now Set your animation
			bmImage.startAnimation(fadeInAnimation);
		}

	}
}
