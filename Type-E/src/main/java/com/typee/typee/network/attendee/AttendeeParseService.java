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
// import com.typee.typee.network.model.Event;

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

	//fire off per row
	public void addAttendeeToEvent(Event eventObject,Map<String, String> columnsNameAndValues,final AttendeeParseListener attendeeParseListener) {
		Attendee attendeeDetails = new Attendee();
		columnsNameAndValues.put(attendeeDetails.getAttendeeEvent,eventObject);

		ParseCloud.callFunctionInBackground("addAttendeeToEvent", columnsNameAndValues, new FunctionCallback<String>() {
	        public void done(String object, ParseException e) {
		        if (e == null) {
	 	           attendeeParseListener.successful();
	               } else {
	               attendeeParseListener.unsuccessful(e);
	            	}
	            }
	        });
		// 	ParseObject parseTable = new ParseObject(tableName);
		// 	Set set = columnsNameAndValues.entrySet();
		// 	Iterator i = set.iterator();

		// 	while (i.hasNext()) {
		// 		Map.Entry entry = (Map.Entry) i.next();
		// 		parseTable.put((String) entry.getKey(), entry.getValue());
		// 	}

		// 	parseTable.put(tableName,eventObject);

		// 	parseTable.saveInBackground(new SaveCallback() {
		// 		@Override
		// 		public void done(ParseException e) {
		// 			if (e != null) {
		// 				// FAILURE
		// 				baseParseListener.unsuccessful(e);
		// 			} else{
		// 			// SUCCESS
		// 				baseParseListener.successful();
		// 			}
		// 		}
		// 	});
		// }
	}

	//fire off per row
	public void deleteEventAttendee(Map<String, String> columnsNameAndValues,final AttendeeParseListener attendeeParseListener) {
        ParseCloud.callFunctionInBackground("deleteEventAttendee", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    attendeeParseListener.successful();
                } else {
                    attendeeParseListener.unsuccessful(e);
                }
            }
        });
	}

	public void updateEventAttendeeDetails(Map<String, String> columnsNameAndValues,final AttendeeParseListener attendeeParseListener) {
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