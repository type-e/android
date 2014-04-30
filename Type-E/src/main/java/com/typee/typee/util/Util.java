package com.typee.typee.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.typee.typee.service.DataService;
import com.typee.typee.ui.base.BaseActivity;

/**
 * Created by Winson Lim on 1/31/14.
 */
public class Util {
	public static final String FRAGMENT_CLASS_NAME = "FRAGMENT_CLASS_NAME";

	private static ProgressDialog ringProgressDialog;

	/**
	 * Checks if String is NULL or empty, after .trim()
	 *
	 * @param str String to be checked
	 *
	 * @return True if NULL/isEmpty(); Else false;
	 */
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	public static String generateToken(String mobileNo) {
		String mobileLast3 = mobileNo.substring(mobileNo.length() - 3);

		String token = "" + System.currentTimeMillis() % 1000;

		return token;
	}

	/**
	 * Verify that this is a Singapore Mobile Number
	 *
	 * @param mobileNo Mobile Number
	 *
	 * @return True if this is a Singapore Mobile Number; Else false;
	 */
	public static boolean verifySingaporeMobileNo(String mobileNo) {

		if (!isNullOrEmpty(mobileNo)) {
			if (mobileNo.length() == 8) {
				if (mobileNo.startsWith("9") || mobileNo.startsWith("8")) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Remove all non-numerical characters.
	 *
	 * @param mobileNo String to be trimmed
	 *
	 * @return A string which only contain all numerical values (No re-arranging)
	 */
	public static String trimMobileNo(String mobileNo) {

		if (!isNullOrEmpty(mobileNo)) {
			mobileNo = mobileNo.replaceAll("[^\\d.]", "");

			// TODO: to not remove country code for globalisation
			if (mobileNo.length() > 8) {
				//check if number starts with SG country code
				if (mobileNo.startsWith("65") || mobileNo.startsWith("65")) {
					mobileNo = mobileNo.substring(2, mobileNo.length());
				}
			}

		}

		return mobileNo;
	}

	/**
	 * Start the data service running in background
	 *
	 * @param context Context of this activity
	 */
	public static void startService(Context context) {
		// use this to start and trigger a service
		Intent i = new Intent(context, DataService.class);

		// potentially add data to the intent
		context.startService(i);
	}

	/**
	 * Start an activity with the Fragment.class.getName()
	 *
	 * @param context           Context of this activity
	 * @param fragmentClassName Fragment.class.getName() of the fragment
	 */
	public static void startActivity(Context context, String fragmentClassName) {

		if (context == null || isNullOrEmpty(fragmentClassName)) return;

		Intent openFragmentInActivityIntent = new Intent(context, BaseActivity.class);
		openFragmentInActivityIntent.putExtra(FRAGMENT_CLASS_NAME, fragmentClassName);

		context.startActivity(openFragmentInActivityIntent);
	}

	/**
	 * Starts an activity with the fragment's class name passed
	 *
	 * @param context           Context of this activity
	 * @param fragmentClassName Fragment.class.getName() of the fragment
	 * @param extras            bundle to be passed to new fragment
	 */
	public static void startActivity(Context context, String fragmentClassName, Bundle extras) {
		if (extras == null) {
			startActivity(context, fragmentClassName);
			return;
		}

		Intent openFragmentInActivityIntent = new Intent(context, BaseActivity.class);
		openFragmentInActivityIntent.putExtra(FRAGMENT_CLASS_NAME, fragmentClassName);
		openFragmentInActivityIntent.putExtras(extras);

		context.startActivity(openFragmentInActivityIntent);
	}

	public static void showProgressDialog(Context context, String message) {
		ringProgressDialog = ProgressDialog.show(context, "Please wait", message, true);

		ringProgressDialog.setCancelable(true);
	}

	public static void hideProgressDialog() {
		if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
			ringProgressDialog.dismiss();

			ringProgressDialog = null;
		}
	}
}
