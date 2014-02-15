package com.typee.typee.network.registration;

import android.net.Uri;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.typee.typee.config.Config;
import com.typee.typee.network.BaseParseService;
import com.typee.typee.ui.main.MainApplication;
import com.typee.typee.util.Util;

/**
 * Created by winsonlim on 20/1/14.
 */
public class RegistrationParseService extends BaseParseService {
	private static RegistrationParseService instance;

	public static RegistrationParseService getParseService() {
		if (instance == null)
			instance = new RegistrationParseService();

		return instance;
	}

	public static void signUp(final String mobileNumber, final RegistrationListener registrationListener) {

		if (mobileNumber == null || registrationListener == null) return;

		final ParseUser user = new ParseUser();
		user.setUsername(mobileNumber);
		user.setPassword(mobileNumber);

		// normal ParseObject
//		user.put("MobileNumber", mobileNumber);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					registrationListener.registerSuccessful();

				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					registrationListener.registerUnsuccessful();
				}
			}
		});
	}

	public static void checkIfUserExists(final String mobileNumber, final FindUserListener findUserListener) {

		if (mobileNumber == null || findUserListener == null) return;

		ParseUser.logInInBackground(mobileNumber, mobileNumber, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// Hooray! The user exists in database.
					findUserListener.userFound();
				} else {
					findUserListener.userNotFound();
				}
			}
		});
	}

	public static void sendRegistrationToken(String mobileNumber, final TokenSentListener tokenSentListener) {

		if (mobileNumber == null || tokenSentListener == null) return;

		final String token = Util.generateToken(mobileNumber);
		final String message = "Type-E verification token: " + token;
		final int languageType = 1;
		mobileNumber = "65" + mobileNumber; // '65' is needed to force SG country code

		final String requestURL = Config.SMS_API_URL + "?apiusername=" + Config.SMS_API_USERNAME + "&apipassword=" + Config.SMS_API_PASSWORD + "&mobileno=" + mobileNumber + "&senderid=" + Config.APP_NAME + "&languagetype=" + languageType + "&message=" + Uri.encode(message);

		StringRequest req = new StringRequest(requestURL, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				tokenSentListener.tokenSentSuccessful(token);

				VolleyLog.v("Response:%n %s", response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				tokenSentListener.tokenSentUnsuccessful();

				VolleyLog.e("Error: ", error.getMessage());
			}
		}
		);

		// add the request object to the queue to be executed
		MainApplication.getInstance().addToRequestQueue(req);

	}
}