package com.typee.typee.network.attendee;

import android.util.Log;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.Attendee;
import com.typee.typee.config.DbConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by winsonlim on 20/1/14.
 */
public class AttendeeParseService {
    public String TAG = this.getClass().getSimpleName();

    private static AttendeeParseService instance;

    public static AttendeeParseService getAttendeeParseService() {
        if (instance == null)
            instance = new AttendeeParseService();

        return instance;
    }

    //fire off per user
    public void addAttendeeToEvent(String eventName, String userName, final SuccessListener successListener) {
        String tableName = DbConfig.prefixForEventAttendee + eventName;

        ParseObject eventAttendee = new ParseObject(tableName);

        eventAttendee.put(Attendee.attendeeNameKey, userName);
        //eventAttendee.put(Attendee.attendeeTaskKey,  taskDescription);
        // eventAttendee.put(attendee.getAttendeeSplittedBill(), null);

        // TODO: no attendeeLocationKey and getAttendeeStatusKey
//		eventAttendee.put(attendee.getAttendeeLocation(), null);
//		eventAttendee.put(attendee.getAttendeeStatusKey(), null);

        eventAttendee.saveInBackground(new SaveCallback() {
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

    public void updateEventAttendeeDetails(String tableName, String objectID, Map<String, String> columnsNameAndValues, final AttendeeParseListener attendeeParseListener) {

        columnsNameAndValues.put("tableName", tableName);
        columnsNameAndValues.put("key", objectID);

        ParseCloud.callFunctionInBackground("updateEventAttendeeDetails", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    attendeeParseListener.successful(object);
                } else {
                    attendeeParseListener.unsuccessful(e);
                }
            }
        });
    }
    
    //return attendee name, status - phrase 1
    //phase 2 - return task, location
    public void getEventAttendee(String tableName, String objectID, final AttendeeParseListener attendeeParseListener){
        tableName = DbConfig.prefixForEventAttendee + tableName;
        
        Map <String, String> tableMap = new HashMap<String, String>();
        
        tableMap.put("tableName",tableName);
        
        ParseCloud.callFunctionInBackground("updateEventAttendeeDetails", tableMap, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    attendeeParseListener.successful(object);
                } else {
                    attendeeParseListener.unsuccessful(e);
                }
            }
        });
    }
}
