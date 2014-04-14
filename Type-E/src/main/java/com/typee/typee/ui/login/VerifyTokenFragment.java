package com.typee.typee.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.typee.typee.R;
import com.typee.typee.config.Config;
import com.typee.typee.model.SMS;
import com.typee.typee.network.registration.RegistrationListener;
import com.typee.typee.network.registration.RegistrationParseService;
import com.typee.typee.sms.SMSTimeOutHandler;
import com.typee.typee.ui.base.BaseFragment;
import com.typee.typee.ui.event.EventDetailsFragment;
import com.typee.typee.ui.main.MainApplication;
import com.typee.typee.util.StoredPreferences;
import com.typee.typee.util.Util;

public class VerifyTokenFragment extends BaseFragment {
	private String token;
	private String mobileNo;

	private View rootView;
	private TextView tokenInstruction;
	private TextView tokenAutoPopulating;
	private EditText tokenEditText;
	private Button verifyButton;
	private ProgressBar tokenProgressBar;

	private Handler handler = new Handler();

	public VerifyTokenFragment() {
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

		rootView = inflater.inflate(R.layout.fragment_verify_token, container, false);
		assert rootView != null;

		tokenAutoPopulating = (TextView) rootView.findViewById(R.id.token_populate);
		assert tokenAutoPopulating != null;

		tokenInstruction = (TextView) rootView.findViewById(R.id.token_instruction);
		assert tokenInstruction != null;

		tokenEditText = (EditText) rootView.findViewById(R.id.edittext_token);
		assert tokenEditText != null;

		tokenProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_token);
		assert tokenProgressBar != null;

		verifyButton = (Button) rootView.findViewById(R.id.button_verify);
		assert verifyButton != null;

		return inflater.inflate(R.layout.template_loading_bar, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setDisplayHomeAsUpEnabled(true);

		setContentView(rootView);
		enableAutoHideKeyboard(rootView);
		hideLoadingIndicator();

		tokenInstruction.setText(String.format(getString(R.string.token_start_msg), mobileNo));

		rootView.findViewById(R.id.button_resend).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resendToken();
			}
		});

		verifyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				verifyToken();
			}
		});

		SMSTimeOutHandler.getTimer().startTime();

		tokenProgressBar.setMax(Config.SMS_TOKEN_TIMEOUT);

		startCountdown();
	}

	@Override
	public void onResume() {
		super.onResume();

		MainApplication.getEventBus().register(this);
	}

	@Override
	public void onPause() {
		super.onPause();

		MainApplication.getEventBus().unregister(this);
	}

	private void verifyToken() {
		String typedToken = tokenEditText.getEditableText().toString();

		showLoadingIndicator();

		if (typedToken.equalsIgnoreCase(token)) {
			// TODO: register the new account here.
			RegistrationParseService.getParseService().signUp(mobileNo, new RegistrationListener() {
				@Override
				public void registerSuccessful() {
					hideLoadingIndicator();
					if (getActivity() == null) return;

					Toast.makeText(getActivity(), mobileNo + " registered!", Toast.LENGTH_SHORT).show();

					StoredPreferences.setMobileNo(mobileNo);

					Util.startActivity(getActivity(), EventDetailsFragment.class.getName());
					finish();
				}

				@Override
				public void registerUnsuccessful() {
					hideLoadingIndicator();
					if (getActivity() == null) return;

					Toast.makeText(getActivity(), mobileNo + " registration FAILED!", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			if (getActivity() != null)
				Toast.makeText(getActivity(), "This token is invalid. Please verify.", Toast.LENGTH_SHORT).show();
		}

		hideLoadingIndicator();
	}

	private void resendToken() {
		// TODO: resend the token
		if (getActivity() != null)
			Toast.makeText(getActivity(), "Re-send token is not done.", Toast.LENGTH_SHORT).show();
	}

	@Subscribe
	public void subscribeEventsSummary(SMS sms) {
		String message = sms.getMessage();

		try {
			// Read token from SMS message body
			String token = message.substring(message.lastIndexOf(' ') + 1);

			populateToken(token);

		} catch (IndexOutOfBoundsException e) {

		}
	}

	private void populateToken(String token) {
		if (getActivity() != null) {
			stopCountdown();

			if (tokenEditText != null) tokenEditText.setText(token);

			if (tokenProgressBar != null) tokenProgressBar.setProgress(0);

			if (tokenAutoPopulating != null) {
				AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
				fadeOut.setDuration(1200);
				fadeOut.setFillAfter(true);
				tokenAutoPopulating.startAnimation(fadeOut);

				tokenAutoPopulating.setVisibility(View.INVISIBLE);
			}

			Toast.makeText(getActivity(), String.format(getString(R.string.populated_token), token, verifyButton.getText().toString()), Toast.LENGTH_LONG).show();

			hideSoftKeyboard();
		}
	}

	private void updateTime() {

		if (tokenProgressBar != null) tokenProgressBar.setProgress(SMSTimeOutHandler.getTimer().getRemainingTime());
	}

	private void startCountdown() {
		if (mStatusChecker != null) mStatusChecker.run();
	}

	private void stopCountdown() {
		if (handler != null && mStatusChecker != null) handler.removeCallbacks(mStatusChecker);
	}

	private Runnable mStatusChecker = new Runnable() {
		@Override
		public void run() {
			if (getActivity() != null) {
				updateTime();

				if (SMSTimeOutHandler.getTimer().isCountdownRunning()) {
					handler.postDelayed(mStatusChecker, 1000);
				} else {
					tokenAutoPopulating.setVisibility(View.INVISIBLE);

					Toast.makeText(getActivity(), "Please check your SMS inbox for the token. If no SMS found, consider 'Resend'", Toast.LENGTH_LONG).show();
				}
			}
		}
	};
}
