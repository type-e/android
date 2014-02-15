package com.typee.typee.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.typee.typee.R;
import com.typee.typee.network.registration.RegistrationListener;
import com.typee.typee.network.registration.RegistrationParseService;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.ui.event.EventDetailsFragment;
import com.typee.typee.ui.login.LoginFragment;
import com.typee.typee.util.Util;

public class RegistrationFragment extends BaseFragment {
	private String token;
	private String mobileNo;

	private View rootView;
	private EditText tokenEditText;

	public RegistrationFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getExtras();
		if (extras != null) {
			if (extras.containsKey(LoginFragment.ID_TOKEN)) token = extras.getString(LoginFragment.ID_TOKEN);
			if (extras.containsKey(LoginFragment.ID_MOBILE)) mobileNo = extras.getString(LoginFragment.ID_MOBILE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		setDisplayHomeAsUpEnabled(true);

		rootView = inflater.inflate(R.layout.fragment_registration, container, false);

		TextView instruction = (TextView) rootView.findViewById(R.id.registration_instruction);
		instruction.setText(String.format(getString(R.string.token_start_msg), mobileNo));

		tokenEditText = (EditText) rootView.findViewById(R.id.edittext_token);

		rootView.findViewById(R.id.button_resend).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resendToken();
			}
		});

		rootView.findViewById(R.id.button_verify).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				verifyToken();
			}
		});

		return rootView;
	}

	private void verifyToken() {
		String typedToken = tokenEditText.getEditableText().toString();

		if (typedToken.equalsIgnoreCase(token)) {
			// TODO: register the new account here.
			RegistrationParseService.getParseService().signUp(mobileNo, new RegistrationListener() {
				@Override
				public void registerSuccessful() {

					if (getActivity() == null) return;

					Toast.makeText(getActivity(), mobileNo + " registration SUCCESSFUL!", Toast.LENGTH_SHORT).show();

					Util.startActivity(getActivity(), EventDetailsFragment.class.getName());
					finish();
				}

				@Override
				public void registerUnsuccessful() {

					if (getActivity() == null) return;

					Toast.makeText(getActivity(), mobileNo + " registration FAILED!", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(getActivity(), "Wrong token keyed in. Please verify.", Toast.LENGTH_SHORT).show();
		}

	}

	private void resendToken() {
	}
}
