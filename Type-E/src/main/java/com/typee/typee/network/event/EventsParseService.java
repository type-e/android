package com.typee.typee.network.event;

import com.parse.FindCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.typee.typee.config.DbConfig;
import com.typee.typee.network.base.BaseParseService;
import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.Attendee;
import com.typee.typee.network.model.Event;

import java.util.HashMap;
import java.util.List;

/**
 * Created by winsonlim on 20/1/14.
 */
public class EventsParseService extends BaseParseService {

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

	public void createEvent(String eventName, String eventDescription, String eventVenue, String eventTime, String username, final SuccessListener successListener) {

		Event eventDetails = new Event();
		eventDetails.setUsername(username);
		eventDetails.setEventDescription(eventDescription);
		eventDetails.setEventName(eventName);
		eventDetails.setEventTime(eventTime);
		eventDetails.setEventVenue(eventVenue);

		// Map Relation of Event to Attendee
		Attendee eventAttendee = new Attendee();
		eventAttendee.setEventAttendee(username);
		eventAttendee.setEvent(eventDetails);

		eventAttendee.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					// FAILURE
					successListener.unsuccessful(e);
				}

				// SUCCESS
				successListener.successful();
			}
		});

	}

	public void deleteEvent(String tableName, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener) {
		try {
			BaseParseService.getBaseParseService().deleteData(DbConfig.eventTable, columnToCompare, columnValue);
			eventsParseListener.successful(null);
		} catch (ParseException e) {
			eventsParseListener.unsuccessful(e);
		}
	}

	public void updateEventDetail(String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener) {
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("tableName", tableName);
//        params.put("columnName", columnToCompare);
//        params.put("columnValue", columnValue);
//        params.put("updateColumn", columnToUpdate);
//        params.put("updateValue", updateValue);
//
//        ParseCloud.callFunctionInBackground("updateRecordInTable", params, new FunctionCallback<String>() {
//            public void done(String object, ParseException e) {
//                if (e == null) {
//                    eventsParseListener.successful();
//                } else {
//                    eventsParseListener.unsuccessful(e);
//                }
//            }
//        });

		try {
			BaseParseService.getBaseParseService().updateColumnValue(DbConfig.eventTable, columnToUpdate, updateValue, columnToCompare, columnValue);
			eventsParseListener.successful(null);
		} catch (ParseException e) {
			eventsParseListener.unsuccessful(e);
		}
	}

	public void addAttendeeToEvent(String userID, String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tableName", tableName);
		params.put("columnName", columnToCompare);
		params.put("columnValue", columnValue);
		params.put("updateColumn", columnToUpdate);
		params.put("updateValue", updateValue);
		params.put("userId", userID);

		try {
			ParseCloud.callFunction("insertUsersIntoEvent", params);
			eventsParseListener.successful(null);
		} catch (ParseException e) {
			eventsParseListener.unsuccessful(e);
		}
	}

	public void getAllEvents(String username, final GetAllEventsListener getAllEventsListener) {

		ParseQuery<Attendee> query = ParseQuery.getQuery(Attendee.class);

		// Get only the event this username is invited to
		query.whereEqualTo(Attendee.eventAttendeeKey, username);

		// Also get the Event object (relational data)
		query.include(DbConfig.eventTable);

		query.findInBackground(new FindCallback<Attendee>() {
			public void done(List<Attendee> results, ParseException e) {
				if (e == null) {
					getAllEventsListener.successful(results);
				} else {
					getAllEventsListener.unsuccessful(e);
				}
			}
		});
	}
}
