package com.typee.typee.ui.main;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

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
}
