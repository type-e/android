package com.typee.typee.network.event;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.typee.typee.network.model.Event;
import com.typee.typee.network.base.UserParseService;
import com.typee.typee.network.base.AttendeeParseService;

/**
 * Created by winsonlim on 20/1/14.
 */
public class EventsParseService {

	private static EventsParseService instance;

	public static EventsParseService getEventsParseService() {
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

	public void createEvent(String eventName,String eventDescription,String eventVenue,Date eventStartDateTime,Date eventEndDateTime,String eventNote,double eventLocationLong,souble eventLocationLat,String eventRecurrence,String username) {

		Event eventDetails = new Event();
		ParseGeoPoint point = new ParseGeoPoint(eventLocationLong, eventLocationLat);
		// eventDetails.setUsername(username);
		eventDetails.setEventDescription(eventDescription);
		eventDetails.setEventName(eventName);
		eventDetails.setEventNote(eventNote);
		eventDetails.setEventLocation(point);
		eventDetails.setEventRecurrence(eventRecurrence);
		eventDetails.setEventStartDateTime(eventStartDateTime);
		eventDetails.setEventEndDateTime(eventEndDateTime);
		eventDetails.setEventVenue(eventVenue);

		String eventAttendeeTableName = eventName + "_Attendee";

		UserParseService userParseService = new UserParseService();
		userParseService.addUserActivity(username,eventDetails);

		AttendeeParseService attendeeParseService = new AttendeeParseService();
		attendeeParseService.addAttendeeToEvent(eventAttendeeTableName,username)
	}

	//query base on per screen information; parse cloud
	public void queryEvent(String eventName, String eventDescription, String eventVenue, String eventTime, String username, final EventsParseListener eventsParseListener) {

	}

	public void updateEventDetails(String tableName,String objectID,Map<String, String> columnsNameAndValues,final EventsParseListener eventsParseListener) {

		columnsNameAndValues.put("tableName",tableName);
        columnsNameAndValues.put("key",objectID);
        
		ParseCloud.callFunctionInBackground("updateEventDetails", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    eventsParseListener.successful();
                } else {
                    eventsParseListener.unsuccessful(e);
                }
            }
        });
	}
}
