package com.typee.typee.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.typee.typee.R;
import com.typee.typee.network.registration.FindUserListener;
import com.typee.typee.network.registration.RegistrationListener;
import com.typee.typee.network.registration.RegistrationParseService;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.ui.event.EventDetailsFragment;
import com.typee.typee.util.Util;

public class LoginFragment extends BaseFragment {
	private View rootView;
	private EditText mobileNoEditText;
	private String mobileNo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_login, container, false);

		mobileNoEditText = (EditText) rootView.findViewById(R.id.edittext_mobile_number);
		mobileNoEditText.setText(mobileNo);

		rootView.findViewById(R.id.button_sign_in).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});
		return inflater.inflate(R.layout.template_loading_bar, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setContentView(rootView);
		hideLoadingIndicator();
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		// Reset errors.
		mobileNoEditText.setError(null);

		// Store values at the time of the login attempt.
		mobileNo = Util.trimMobileNo(mobileNoEditText.getText().toString());

		boolean cancel = false;
		View focusView = null;

		// Check for a valid mobile number.
		if (TextUtils.isEmpty(mobileNo)) {
			mobileNoEditText.setError(getString(R.string.error_field_required));
			focusView = mobileNoEditText;
			cancel = true;
		} else if (!Util.verifySingaporeMobileNo(mobileNo)) {
			mobileNoEditText.setError(getString(R.string.error_invalid_mobile_number));
			focusView = mobileNoEditText;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to perform the user login attempt.
			showLoadingIndicator();
			RegistrationParseService.getParseService().checkIfUserExists(mobileNo, new FindUserListener() {
				@Override
				public void userFound() {
					hideLoadingIndicator();

					if (getActivity() == null) return;

					Toast.makeText(getActivity(), mobileNo, Toast.LENGTH_SHORT).show();

					Util.startActivity(getActivity(), EventDetailsFragment.class.getName());

					finish();
				}

				@Override
				public void userNotFound() {
					// TODO: register the new account here.
					RegistrationParseService.getParseService().signUp(mobileNo, new RegistrationListener() {
						@Override
						public void registerSuccessful() {
							hideLoadingIndicator();

							if (getActivity() == null) return;

							Toast.makeText(getActivity(), mobileNo + " registration SUCCESSFUL!", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void registerUnsuccessful() {
							hideLoadingIndicator();

							if (getActivity() == null) return;

							Toast.makeText(getActivity(), mobileNo + " registration FAILED!", Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
		}
	}
}
