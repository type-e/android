package com.typee.typee.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.typee.typee.ui.global.ViewFragmentTitles;
import com.typee.typee.util.StoredPreferences;

/**
 * Created by winsonlim on 25/4/14.
 */
public class DataService extends Service {
	public String TAG = this.getClass().getSimpleName();

	private final static ViewFragmentTitles[] values = ViewFragmentTitles.values();

	private static DataService service;

	private IBinder mBinder = new LocalBinder();

	public DataService() {
		// Empty Constructor
	}

	public static DataService getService() {
		if (service == null) {
			service = new DataService();
		}

		return service;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();

//		MainApplication.getEventBus().register(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

//		MainApplication.getEventBus().unregister(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public DataService getDataService() {
			return DataService.this;
		}
	}

	public void getLatestEvent(String position, final DataServiceListener serviceListener) {
		if (serviceListener == null) throw new NullPointerException();

		String mobileNo = StoredPreferences.getMobileNo();
		if (mobileNo == null) throw new NullPointerException();


		//TODO; get latest events

//		EventsParseService.getEventsParseService().getAllEvents(mobileNo, new GetAllEventsListener() {
//			@Override
//			public void successful(List<Attendee> data) {
//				serviceListener.onDataReceived(data);
//			}
//
//			@Override
//			public void unsuccessful(ParseException e) {
//				serviceListener.onDataError(e);
//			}
//		});
	}
}
