package com.typee.typee.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.typee.typee.R;
import com.typee.typee.network.registration.FindUserListener;
import com.typee.typee.network.registration.RegistrationParseService;
import com.typee.typee.network.registration.TokenSentListener;
import com.typee.typee.network.registration.TokenService;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.ui.main.MainActivity;
import com.typee.typee.util.StoredPreferences;
import com.typee.typee.util.Util;

public class LoginFragment extends BaseFragment {
	public static final String ID_TOKEN = "TOKEN_IDENTIFIER";
	public static final String ID_MOBILE = "MOBILE_IDENTIFIER";

	private View rootView;
	private EditText mobileNoEditText;
	private String mobileNo;

	public LoginFragment() {
		// Empty Constructor
	}

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
		enableAutoHideKeyboard(rootView);
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

					Toast.makeText(getActivity(), mobileNo + " saved!", Toast.LENGTH_SHORT).show();

					StoredPreferences.setMobileNo(mobileNo);

					Intent openFragmentInActivityIntent = new Intent(getActivity(), MainActivity.class);

					startActivity(openFragmentInActivityIntent);

					finish();
				}

				@Override
				public void userNotFound() {
					// Call SMS Gateway API here!
					TokenService.getService().sendSMSToken(mobileNo, new TokenSentListener() {
						@Override
						public void tokenSentSuccessful(String token) {
							hideLoadingIndicator();

							if (getActivity() == null) return;

							Bundle extras = new Bundle();
							extras.putString(ID_TOKEN, token);
							extras.putString(ID_MOBILE, mobileNo);

							Util.startActivity(getActivity(), VerifyTokenFragment.class.getName(), extras);
						}

						@Override
						public void tokenSentUnsuccessful() {
							hideLoadingIndicator();

							if (getActivity() == null) return;

							mobileNoEditText.setError(getString(R.string.error_invalid_mobile_number));
							mobileNoEditText.requestFocus();
						}
					});
				}
			});
		}
	}
}
