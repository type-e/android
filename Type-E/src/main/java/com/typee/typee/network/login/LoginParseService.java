package com.typee.typee.network.login;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.typee.typee.network.login.LoginListener;


public class LoginParseService {

	private static LoginParseService instance;

	public static LoginParseService getLoginParseService() {
		if (instance == null)
			instance = new LoginParseService();

		return instance;
	}

	public boolean logout() {
		ParseUser.logOut();
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			return true;
		} else {
			return false;
		}
	}

	public void login(String username, String password, final LoginListener loginListener) {
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					loginListener.successful(user);
				} else {
					loginListener.unsuccessful(e);
				}
			}
		});
	}
}