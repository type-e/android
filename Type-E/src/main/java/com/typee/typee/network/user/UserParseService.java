package com.typee.typee.network.user;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
// import com.typee.typee.config.DbConfig;
// import com.typee.typee.network.base.BaseParseService;
// import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.User;

import java.util.HashMap;
import java.util.Map;
// import java.util.List;

/**
 * Created by winsonlim on 20/1/14.
 */
public class UserParseService {

	public void addUserActivity(String userName,ParseObject parseObject,final UserParseListener userParseListener){

		ParseObject userActivity = new ParseObject(userName+"_Activities");
		User user = new User();

		userActivity.put(user.getUserActivity(), parseObject);
		userActivity.saveInBackground(saveInBackground(new SaveCallback() {
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