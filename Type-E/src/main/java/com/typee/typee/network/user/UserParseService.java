package com.typee.typee.network.user;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.Attendee;
import com.typee.typee.network.model.Event;
import com.typee.typee.network.model.User;

/**
 * Created by winsonlim on 20/1/14.
 */
public class UserParseService {
    public String TAG = this.getClass().getSimpleName();

    public void addUserActivity(String userName, Event eventDetails, final SuccessListener successListener) {
        String tableName = "Activities_" + userName;

        ParseObject userActivity = new ParseObject(tableName);
        userActivity.put(User.userActivityKey, eventDetails);

        userActivity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // FAILURE

                    Log.e(TAG, e.getCode() + ": " + e.getLocalizedMessage());
//                    successListener.unsuccessful(e);
                } else {
                    // SUCCESS
//                    successListener.successful();
                }
            }
        });
    }

    public void addUsersActivity(String userName, Attendee[] attendees, Event eventDetails, final SuccessListener successListener) {
        String tableName = "Activities_" + userName;

        ParseObject userActivity = new ParseObject(tableName);
        userActivity.put(User.userActivityKey, eventDetails);


        // TODO: insert multiple

        userActivity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // FAILURE

                    Log.e(TAG, e.getCode() + ": " + e.getLocalizedMessage());
//                    successListener.unsuccessful(e);
                } else {
                    // SUCCESS
//                    successListener.successful();
                }
            }
        });
    }
}