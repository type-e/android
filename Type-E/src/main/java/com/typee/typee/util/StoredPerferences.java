package com.typee.typee.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.typee.typee.ui.main.MainApplication;

/**
 * Created by winsonlim on 11/4/14.
 */
public class StoredPerferences {
	private static String appKey = "com.typee.typee";

	private static String mobileNoKey = "com.typee.typee.mobileno";

	public static boolean setMobileNo(String mobileNo) {
		SharedPreferences prefs = MainApplication.getContext().getSharedPreferences(
				appKey, Context.MODE_PRIVATE);

		return prefs.edit().putString(mobileNoKey, mobileNo).commit();
	}

	public static String getMobileNo() {
		SharedPreferences prefs = MainApplication.getContext().getSharedPreferences(
				appKey, Context.MODE_PRIVATE);

		return prefs.getString(mobileNoKey, null);
	}
}
