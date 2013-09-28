package com.aranda.android.imdb_client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.Toast;

public class Util {

	public static String decimalToDMS(double coord) {
		String output, degrees, minutes, seconds;

		// gets the modulus the coordinate divided by one (MOD1).
		// in other words gets all the numbers after the decimal point.
		// e.g. mod = 87.728056 % 1 == 0.728056
		//
		// next get the integer part of the coord. On other words the whole
		// number part.
		// e.g. intPart = 87

		double mod = coord % 1;
		int intPart = (int) coord;

		// set degrees to the value of intPart
		// e.g. degrees = "87"

		degrees = String.valueOf(intPart);

		// next times the MOD1 of degrees by 60 so we can find the integer part
		// for minutes.
		// get the MOD1 of the new coord to find the numbers after the decimal
		// point.
		// e.g. coord = 0.728056 * 60 == 43.68336
		// mod = 43.68336 % 1 == 0.68336
		//
		// next get the value of the integer part of the coord.
		// e.g. intPart = 43

		coord = mod * 60;
		mod = coord % 1;
		intPart = (int) coord;

		// set minutes to the value of intPart.
		// e.g. minutes = "43"
		minutes = String.valueOf(intPart);

		// do the same again for minutes
		// e.g. coord = 0.68336 * 60 == 41.0016
		// e.g. intPart = 41
		coord = mod * 60;
		intPart = (int) coord;

		// set seconds to the value of intPart.
		// e.g. seconds = "41"
		seconds = String.valueOf(intPart);

		// I used this format for android but you can change it
		// to return in whatever format you like
		// e.g. output = "87/1,43/1,41/1"
		// output = degrees + "/1," + minutes + "/1," + seconds + "/1";

		// Standard output of D??????M?????????S?????????
		output = degrees + "??????" + minutes + "'" + seconds + "\"";

		return output;
	}

	public static String dateToString(Date date, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	public static Date stringToDate(String strDate, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void showToast(Context context, String message, int duration) {
		Toast.makeText(context, message, duration).show();
	}

	public static Bitmap rotateBitmap(Bitmap bitmapOriginal, int grados,
			boolean recycle) {
		if (grados != 0) {
			int width = bitmapOriginal.getWidth();
			int height = bitmapOriginal.getHeight();
			Matrix matrix = new Matrix();
			matrix.postRotate((float) grados);
			Bitmap result = Bitmap.createBitmap(bitmapOriginal, 0, 0, width,
					height, matrix, true);
			if (recycle) {
				bitmapOriginal.recycle();
			}

			bitmapOriginal = result;
		}

		return bitmapOriginal;
	}

	public static String dateNowToString(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	public static boolean isValidString(String string) {

		Pattern pattern;
		Matcher matcher;

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@";

		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(string);
		return matcher.matches();

	}

}
