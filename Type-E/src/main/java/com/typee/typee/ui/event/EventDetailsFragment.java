package com.typee.typee.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jake.quiltview.QuiltView;
import com.typee.typee.R;
import com.typee.typee.ui.base.BaseFragment;

import java.util.ArrayList;

public class EventDetailsFragment extends BaseFragment {

    private View rootView;
    private QuiltView quiltView;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event_details, container, false);
        quiltView = (QuiltView) rootView.findViewById(R.id.event_attendee_quilt);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        quiltView.setChildPadding(5);
        quiltView.setOrientation(false);

        addTestQuilts(200);
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
}
