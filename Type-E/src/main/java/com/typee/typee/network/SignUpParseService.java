package com.typee.typee.network;

/**
 * Created by winsonlim on 20/1/14.
 */
public class SignUpParseService extends BaseParseService {
    private static SignUpParseService instance;


    public static SignUpParseService getParseService() {
        if (instance == null)
            instance = new SignUpParseService();

        return instance;
    }

    public static void signUp(String username, String password, String emailAddress, int mobileNumber) {
        // todo to decide on uuid of registered users (mobile or email)
    }
}