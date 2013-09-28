package com.aranda.android.imdb_client.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppStatus {

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isLocationProviderEnable(String provider,
			Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(provider);
	}
}
