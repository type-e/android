package com.typee.typee.ui.event;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.typee.typee.R;
import com.typee.typee.service.DataServiceListener;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.util.Util;

import java.util.Date;

public class EventDetailsFragment extends BaseFragment implements DataServiceListener {
	private static final String EVENT_TITLE = "event_title";

	private View rootView;
	private Button addEvent;

	private String title;

	public EventDetailsFragment() {
		// Empty Constructor
	}

	public static EventDetailsFragment newInstance(String title) {
		EventDetailsFragment fragment = new EventDetailsFragment();
		Bundle args = new Bundle();
		args.putString(EVENT_TITLE, title);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			this.title = getArguments().getString(EVENT_TITLE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_event_details, container, false);

		addEvent = (Button) rootView.findViewById(R.id.button_event_add);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (title != null) {
			// TODO: Here is how you test a method call run time
			Date now = new Date();

//			DataService.getService().getLatestEvent(title, this);

			Date then = new Date();
			Log.e(TAG, "getLatestEvent() took: " + (then.getTime() - now.getTime()) + "ms");
		}

		addEvent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.startActivity(getActivity(), AddEventFragment.class.getName());
			}
		});

		// TODO: Here is how you set plurals in code
//	    countdownUnitTextView.setText(getResources().getQuantityString(R.plurals.event_timeout_units_minutes, minutes));
	}

	@Override
	public void onDataReceived(Object data) {
		if (data != null) {
			if (getActivity() != null)
				Toast.makeText(getActivity(), "onDataReceived: " + data.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDataError(Object error) {
		if (getActivity() != null)
			Toast.makeText(getActivity(), "onDataError: " + error.toString(), Toast.LENGTH_SHORT).show();
	}
}
