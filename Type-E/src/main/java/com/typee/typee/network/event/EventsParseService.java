package com.typee.typee.network.event;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.typee.typee.network.attendee.AttendeeParseService;
import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.Event;
import com.typee.typee.network.user.UserParseService;

import java.util.Map;

/**
 * Created by winsonlim on 20/1/14.
 */
public class EventsParseService {

    private static EventsParseService instance;

    public static synchronized EventsParseService getEventsParseService() {
        if (instance == null)
            instance = new EventsParseService();

        return instance;
    }

	/*
    TODO change event data structure

	When inserting a new event the following fields must be included:
		dtstart
		dtend if the event is non-recurring
		duration if the event is recurring
		rrule or rdate if the event is recurring
		eventTimezone
		a calendar_id
	There are also further requirements when inserting or updating an event. See the section on Writing to Events.
	 */

    //	public void createEvent(String eventName, String eventDescription, String eventVenue, Date eventStartDateTime, Date eventEndDateTime, String eventNote, double eventLocationLong, double eventLocationLat, String eventRecurrence, String username) {
    public void createEvent(String eventName, String eventDescription, String eventVenue, String username, SuccessListener successListener) {

        Boolean callDone = false;

        Event eventDetails = new Event();
        // ParseGeoPoint point = new ParseGeoPoint(eventLocationLong, eventLocationLat);
        // eventDetails.setUsername(username);
        eventDetails.setEventDescription(eventDescription);
        eventDetails.setEventName(eventName);
        // eventDetails.setEventNote(eventNote);
        // eventDetails.setEventLocation(point);
        // eventDetails.setEventRecurrence(eventRecurrence);
        // eventDetails.setEventStartDateTime(eventStartDateTime);
        // eventDetails.setEventEndDateTime(eventEndDateTime);
        eventDetails.setEventVenue(eventVenue);

        UserParseService userParseService = new UserParseService();
        //todo add listener
        userParseService.addUserActivity(username, eventDetails, new SuccessListener() {
            @Override
            public void successful() {
                //todo: set flag to call successListener
            }

            @Override
            public void unsuccessful(ParseException e) {

            }
        });

        AttendeeParseService attendeeParseService = new AttendeeParseService();
        //todo add listener
        attendeeParseService.addAttendeeToEvent(eventName, username, null);

        // call back to listeners with 2 flags
    }

    //query base on per screen information; parse cloud
    public void queryEvent(String eventName, String eventDescription, String eventVenue, String eventTime, String username, final EventsParseListener eventsParseListener) {

    }

    public void updateEventDetails(String tableName, String objectID, Map<String, String> columnsNameAndValues, final EventsParseListener eventsParseListener) {

        columnsNameAndValues.put("tableName", tableName);
        columnsNameAndValues.put("key", objectID);

        ParseCloud.callFunctionInBackground("updateEventDetails", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    eventsParseListener.successful(object);
                } else {
                    eventsParseListener.unsuccessful(e);
                }
            }
        });
    }
}
