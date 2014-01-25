package com.typee.typee.network.registration;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
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

    public static void signUp(String username, String password, String emailAddress, int mobileNumber, final RegistrationCallback registerCallback) {
        // todo to decide on uuid of registered users (mobile or email)

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(emailAddress);

        // normal ParseObject
        user.put("MobileNumber", mobileNumber);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    registerCallback.registerSuccessful();
                } else {
                    registerCallback.registerUnsuccessful();
                }
            }
        });
    }
}