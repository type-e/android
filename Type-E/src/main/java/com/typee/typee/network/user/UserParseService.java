package com.typee.typee.network.user;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.typee.typee.network.model.User;

/**
 * Created by winsonlim on 20/1/14.
 */
public class UserParseService {

	public void addUserActivity(String userName, Event eventDetails, final UserParseListener userParseListener) {

		ParseObject userActivity = new ParseObject(userName + "_Activities");
		User user = new User();

		userActivity.put(user.getUserActivity(), eventDetails);
		userActivity.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					// FAILURE
					userParseListener.unsuccessful(e);
				} else {
					// SUCCESS
					userParseListener.successful();
				}
			}
		});
	}
}