package com.typee.typee.network.attendee;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
// import com.typee.typee.config.DbConfig;
// import com.typee.typee.network.base.BaseParseService;
// import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.Attendee;
import com.typee.typee.network.model.Event;

import java.util.HashMap;
import java.util.List;

/**
 * Created by winsonlim on 20/1/14.
 */
public class AttendeeParseService {

	private static AttendeeParseService instance;
	
	public static AttendeeParseService getAttendeeParseService() {
		if (instance == null)
			instance = new AttendeeParseService();

		return instance;
	}

	//fire off per user
	public void addAttendeeToEvent(String eventName,String username,final AttendeeParseListener attendeeParseListener){

		ParseObject eventAttendee = new ParseObject(eventName+"_Attendee");
		Attendee attendee = new Attendee();
		
		eventAttendee.put(attendee.getAttendeeName(), username);
		eventAttendee.saveInBackground(saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					// FAILURE
					attendeeParseListener.unsuccessful(e);
				} else {
				// SUCCESS
					attendeeParseListener.successful();
				}
			}
		});
	}

	public void updateEventAttendeeDetails(String tableName,String objectID,Map<String, String> columnsNameAndValues,final AttendeeParseListener attendeeParseListener) {
        
        columnsNameAndValues.put("tableName",tableName);
        columnsNameAndValues.put("key",objectID);
        
        ParseCloud.callFunctionInBackground("updateEventAttendeeDetails", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    attendeeParseListener.successful();
                } else {
                    attendeeParseListener.unsuccessful(e);
                }
            }
        });
	}
}