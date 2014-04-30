package com.typee.typee.ui.main;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.deploygate.sdk.DeployGate;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.typee.typee.config.Config;
import com.typee.typee.network.model.Attendee;
import com.typee.typee.network.model.Event;
import com.typee.typee.network.model.User;
import com.typee.typee.service.DataService;

/**
 * Created by Winson Lim on 1/26/14.
 */
public class MainApplication extends Application {
	public static final String TAG = "TypeEVolley";

	private static Bus mEventBus;

	public boolean mBounded;
	public DataService mServer;

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

		registerParse();

		mEventBus = new Bus(ThreadEnforcer.ANY);

		mRequestQueue = Volley.newRequestQueue(this);

		Intent mIntent = new Intent(getApplicationContext(), DataService.class);

		this.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);

		/*
		// STOP Service
		if (mBounded) {
			unbindService(mConnection);
			mBounded = false;
		}
		*/
	}

	private void registerParse() {
		// Add your initialization code here
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access while disabling public write access.
//		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

		ParseObject.registerSubclass(User.class);
		ParseObject.registerSubclass(Event.class);
		ParseObject.registerSubclass(Attendee.class);
		Parse.initialize(this, Config.PARSE_APPID, Config.PARSE_CLIENT_KEY);
	}

	public static Bus getEventBus() {
		return mEventBus;
	}

	/**
	 * Connection for DataService
	 */
	ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceDisconnected(ComponentName name) {
			mBounded = false;
			mServer = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			mBounded = true;

			DataService.LocalBinder mLocalBinder = (DataService.LocalBinder) service;
			mServer = mLocalBinder.getDataService();
		}
	};

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
