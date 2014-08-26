package com.typee.typee.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.typee.typee.R;
import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.event.EventsParseService;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.util.StoredPreferences;
import com.typee.typee.util.Util;

import java.util.Date;

/**
 * Created by winsonlim on 16/3/14.
 */
public class AddEventFragment extends BaseFragment implements SuccessListener {

	private View rootView;
	private EditText eventNameEditText;
	private EditText eventDescriptionEditText;
	private EditText eventVenueEditText;
	private EditText eventDateTimeEditText;
	private Button createEventButton;

	public AddEventFragment() {
		// Empty Constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_add_event, container, false);
		assert rootView != null;

		eventNameEditText = (EditText) rootView.findViewById(R.id.edittext_event_name);
		eventDescriptionEditText = (EditText) rootView.findViewById(R.id.edittext_event_description);
		eventVenueEditText = (EditText) rootView.findViewById(R.id.edittext_event_venue);
		eventDateTimeEditText = (EditText) rootView.findViewById(R.id.edittext_event_datetime);

		createEventButton = (Button) rootView.findViewById(R.id.button_event_create);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		createEventButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createEvent();
			}
		});
	}

	private void createEvent() {
		final String username = StoredPreferences.getMobileNo();

		if (!Util.isNullOrEmpty(username)) {
			if (!Util.isNullOrEmpty(eventNameEditText.getText().toString())) {
				if (!Util.isNullOrEmpty(eventDescriptionEditText.getText().toString())) {
					if (!Util.isNullOrEmpty(eventVenueEditText.getText().toString())) {
						if (!Util.isNullOrEmpty(eventDateTimeEditText.getText().toString())) {

							// TODO: implement listener or use Parse's saveEventually
//							Util.showProgressDialog(getActivity(), "Creating event...");
							Toast.makeText(getActivity(), "Event created!", Toast.LENGTH_SHORT).show();

							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										EventsParseService.getEventsParseService().createEvent(
												eventNameEditText.getText().toString(),
												eventDescriptionEditText.getText().toString(),
												eventVenueEditText.getText().toString(),
												new Date(System.currentTimeMillis()),
												new Date(System.currentTimeMillis()),
												"eventNote",
												0L,
												0L,
												"eventRecurrence",
												username);
									} catch (Exception e) {
										// do nothing
									}
								}
							}).start();

						}
					}
				}
			}
		}
	}

	@Override
	public void successful() {
		Util.hideProgressDialog();

		Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	public void unsuccessful(ParseException e) {
		Util.hideProgressDialog();

		Toast.makeText(getActivity(), "Unsucccessful!", Toast.LENGTH_SHORT).show();
	}
}
