package com.typee.typee.ui.main;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.deploygate.sdk.DeployGate;
import com.parse.Parse;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.typee.typee.config.Config;

/**
 * Created by Winson Lim on 1/26/14.
 */
public class MainApplication extends Application {
	public static final String TAG = "TypeEVolley";

	private static Bus mEventBus;

	public static Bus getEventBus() {
		return mEventBus;
	}


	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;

	/**
	 * A singleton instance of the application class for easy access in other places
	 */
	private static MainApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		DeployGate.install(this);

		// Add your initialization code here
		Parse.initialize(this, Config.PARSE_APPID, Config.PARSE_CLIENT_KEY);

		mEventBus = new Bus(ThreadEnforcer.ANY);

		mRequestQueue = Volley.newRequestQueue(this);
	}

	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized MainApplication getInstance() {
		return instance;
	}

	/**
	 * @return ApplicationController Context
	 */
	public static Context getContext() {
		return instance;
		// or return instance.getApplicationContext();
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified
	 * then it is used else Default TAG is used.
	 *
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty

		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 *
	 * @param req
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important
	 * to specify a TAG so that the pending/ongoing requests can be cancelled.
	 *
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
