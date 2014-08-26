package com.typee.typee.network.registration;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.typee.typee.config.Config;
import com.typee.typee.util.Util;

import java.io.IOException;

/**
 * Created by winsonlim on 15/2/14.
 */
public class TokenService {
	private final String TAG = this.getClass().getSimpleName();

	private static TokenService instance;

	public static synchronized TokenService getService() {
		if (instance == null)
			instance = new TokenService();

		return instance;
	}

	public Call sendSMSToken(String mobileNumber, final TokenSentListener tokenSentListener) {
		if (mobileNumber == null || tokenSentListener == null) return null;

		final String token = Util.generateToken(mobileNumber);
		final String message = Config.APP_NAME + " verification token: " + token;
		final int languageType = 1;

		// TODO: implement country code in UI
		mobileNumber = "65" + mobileNumber; // '65' is needed to force SG country code

		final String requestURL = Config.SMS_API_URL + "?apiusername=" + Config.SMS_API_USERNAME + "&apipassword=" + Config.SMS_API_PASSWORD + "&mobileno=" + mobileNumber + "&senderid=" + Config.APP_NAME + "&languagetype=" + languageType + "&message=" + Uri.encode(message);

//		RequestBody formBody = new FormEncodingBuilder()
//				.add("apiusername", Config.SMS_API_USERNAME)
//				.add("apipassword", Config.SMS_API_PASSWORD)
//				.add("mobileno", mobileNumber)
//				.add("senderid", Config.APP_NAME)
//				.add("languagetype", String.valueOf(languageType))
//				.add("message", Uri.encode(message))
//				.build();

		Request request = new Request.Builder()
				.url(requestURL)
//				.post(formBody)
				.build();

		Log.d(TAG, request.toString());

		OkHttpClient client = new OkHttpClient();
		final Call newCall = client.newCall(request);

		newCall.enqueue(new Callback() {
			Handler mainHandler = new Handler(Looper.getMainLooper());

			@Override
			public void onFailure(Request request, IOException e) {
				e.printStackTrace();

				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						tokenSentListener.tokenSentUnsuccessful();
					}
				});
			}

			@Override
			public void onResponse(Response response) throws IOException {
				if (!response.isSuccessful()) {
					tokenSentListener.tokenSentUnsuccessful();

					return;
				}

				//TODO: debug okHttp headers
				Headers responseHeaders = response.headers();
				for (int i = 0; i < responseHeaders.size(); i++) {
					System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
				}
				System.out.println(response.body().string());

				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						tokenSentListener.tokenSentSuccessful(token);
					}
				});
			}
		});

		return newCall;
	}
}
