package com.typee.typee.network.registration;

import android.net.Uri;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.typee.typee.config.Config;
import com.typee.typee.ui.main.MainApplication;
import com.typee.typee.util.Util;

/**
 * Created by winsonlim on 15/2/14.
 */
public class TokenService {

	private static TokenService instance;

	public static TokenService getService() {
		if (instance == null)
			instance = new TokenService();

		return instance;
	}

	public void sendSMSToken(String mobileNumber, final TokenSentListener tokenSentListener) {

		if (mobileNumber == null || tokenSentListener == null) return;

		final String token = Util.generateToken(mobileNumber);
		final String message = Config.APP_NAME + " verification token: " + token;
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
		});

		// add the request object to the queue to be executed
		MainApplication.getInstance().addToRequestQueue(req);
	}
}
