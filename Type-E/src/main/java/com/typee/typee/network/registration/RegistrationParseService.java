package com.typee.typee.network.registration;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.SaveCallback;
import com.typee.typee.network.BaseParseService;

/**
 * Created by winsonlim on 20/1/14.
 */
public class RegistrationParseService extends BaseParseService {
	private static RegistrationParseService instance;

	public static RegistrationParseService getParseService() {
		if (instance == null)
			instance = new RegistrationParseService();

		return instance;
	}

	public void signUp(final String mobileNumber, final RegistrationListener registrationListener) {

		if (mobileNumber == null || registrationListener == null) return;

		final ParseUser user = new ParseUser();
		user.setUsername(mobileNumber);
		user.setPassword(mobileNumber);

		// normal ParseObject
//		user.put("MobileNumber", mobileNumber);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					registrationListener.registerSuccessful();

				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					registrationListener.registerUnsuccessful();
				}
			}
		});
	}

	//call parse cloud
	public void setUserDetails(final String firstName, final String lastName, final String emailAddress, final int addressFK, final String birthday, final String gender, final String country, final RegistrationListener registrationListener) {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
        	// set details
        	currentUser.setEmail(emailAddress);
        	// normal ParseObject
	        currentUser.put("FirstName", firstName);
	        currentUser.put("LastName", lastName);
	        currentUser.put("MobileNumber", mobileNumber);
	        currentUser.put("AddressFK", addressFK);
	        currentUser.put("Birthday", birthday);
	        currentUser.put("Gender", gender);
	        currentUser.put("Country", country);
        } else {
        	// current user does not exists
        }

        currentUser.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Success!
                    registrationListener.setUserDetailsSuccessful();

                } else {
                    // Failure!; connetion error probably
                    registrationListener.setUserDetailsUnsuccessful();
                }
            }
        });
    }

	public void checkIfUserExists(final String mobileNumber, final FindUserListener findUserListener) {

		if (mobileNumber == null || findUserListener == null) return;

		ParseUser.logInInBackground(mobileNumber, mobileNumber, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// Hooray! The user exists in database.
					findUserListener.userFound();
				} else {
					findUserListener.userNotFound();
				}
			}
		});
	}
}