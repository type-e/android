package com.typee.typee.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jake.quiltview.QuiltView;
import com.typee.typee.R;
import com.typee.typee.service.DataService;
import com.typee.typee.service.DataServiceListener;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.util.Util;

import java.util.ArrayList;

public class EventDetailsFragment extends BaseFragment implements DataServiceListener {
	private static final String ARG_POSITION = "position";

	private View rootView;
	private QuiltView quiltView;
	private Button addEvent;

	private String title;

	public EventDetailsFragment() {
		// Empty Constructor
	}

	@Override
	public String getTitle() {
		return title;
	}

	public EventDetailsFragment(CharSequence title) {
		super();

		this.title = title.toString();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_event_details, container, false);

		quiltView = (QuiltView) rootView.findViewById(R.id.event_attendee_quilt);
		addEvent = (Button) rootView.findViewById(R.id.button_event_add);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		quiltView.setChildPadding(5);
		quiltView.setOrientation(false);

		if (title != null) {
			DataService.getService().getLatestEvent(title, this);
		}

		addTestQuilts(10);

		addEvent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.startActivity(getActivity(), AddEventFragment.class.getName());
			}
		});

		// TODO: Here is how you set plurals in code
//	    countdownUnitTextView.setText(getResources().getQuantityString(R.plurals.event_timeout_units_minutes, minutes));
	}

	public void addTestQuilts(int num) {
		ArrayList<ImageView> images = new ArrayList<ImageView>();
		for (int i = 0; i < num; i++) {
			ImageView image = new ImageView(getActivity());
			image.setScaleType(ImageView.ScaleType.CENTER_CROP);
			if (i % 3 == 0)
				image.setImageResource(R.drawable.winson);
			else if (i % 3 == 1)
				image.setImageResource(R.drawable.gx);
			else
				image.setImageResource(R.drawable.candy);

			images.add(image);
		}
		quiltView.addPatchImages(images);
	}

	@Override
	public void onDataReceived(Object data) {
		if (getActivity() != null)
			Toast.makeText(getActivity(), "onDataReceived: " + data.toString(), Toast.LENGTH_SHORT).show();

		if(data != null) {

		}
	}

	@Override
	public void onDataError(Object error) {
		if (getActivity() != null)
			Toast.makeText(getActivity(), "onDataError: " + error.toString(), Toast.LENGTH_SHORT).show();
	}
}
