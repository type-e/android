package com.typee.typee.util;

import android.content.Context;
import android.content.Intent;

import com.typee.typee.ui.base.BaseActivity;

/**
 * Created by Winson Lim on 1/31/14.
 */
public class Util {
	public static final String FRAGMENT_CLASS_NAME = "FRAGMENT_CLASS_NAME";

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

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

	public static void startActivity(Context context, String className) {

		if (context == null || isNullOrEmpty(className)) return;

		Intent openFragmentInActivityIntent = new Intent(context, BaseActivity.class);
		openFragmentInActivityIntent.putExtra(FRAGMENT_CLASS_NAME, className);

		context.startActivity(openFragmentInActivityIntent);
	}

}
