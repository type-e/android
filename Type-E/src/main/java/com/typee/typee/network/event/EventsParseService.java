package com.typee.typee.network.event;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.typee.typee.config.DbConfig;
import com.typee.typee.network.base.BaseParseService;
import com.typee.typee.network.base.SuccessListener;
import com.typee.typee.network.model.Attendee;
import com.typee.typee.network.model.Event;

import java.util.Map;
// import java.util.List;

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

	public void createEvent(String eventName,String eventDescription,String eventVenue,String eventTime,String username,Map<String, String> columnsNameAndValues,final EventsParseListener eventsParseListener) {

		Event eventDetails = new Event();
		// eventDetails.setUsername(username);
		eventDetails.setEventDescription(eventDescription);
		eventDetails.setEventName(eventName);
		eventDetails.setEventNote(eventName);
		eventDetails.setEventLocation(eventTime);
		eventDetails.setEventRecurrence(eventTime);
		eventDetails.setEventStartDateTime(eventTime);
		eventDetails.setEventEndDateTime(eventTime);
		eventDetails.setEventVenue(eventVenue);

		// Map Relation of Event to Attendee
		AttendeeParseService attendeeParseService = new AttendeeParseService();

		if (columnsNameAndValues != null){
			attendeeParseService.addAttendeeToEvent(eventDetails,columnsNameAndValues);
		}
		else{
			eventDetails.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					if (e != null) {
						// FAILURE
						eventsParseListener.unsuccessful(e);
					} else {
						// SUCCESS
						eventsParseListener.successful();
					}
				}
			});
		}
	}

	public void deleteEvent(Map<String, String> columnsNameAndValues,final EventsParseListener eventsParseListener) {
        ParseCloud.callFunctionInBackground("deleteEvent", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    eventsParseListener.successful();
                } else {
                    eventsParseListener.unsuccessful(e);
                }
            }
        });
	}

	//query base on per screen information; parse cloud
	public void queryEvent(String eventName, String eventDescription, String eventVenue, String eventTime, String username, final EventsParseListener eventsParseListener) {

	}

	public void updateEvent(Map<String, String> columnsNameAndValues,final EventsParseListener eventsParseListener) {
		ParseCloud.callFunctionInBackground("updateEvent", columnsNameAndValues, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    eventsParseListener.successful();
                } else {
                    eventsParseListener.unsuccessful(e);
                }
            }
        });
	}

	// public void addAttendeeToEvent(String userID, String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener) {
	// 	HashMap<String, String> params = new HashMap<String, String>();
	// 	params.put("tableName", tableName);
	// 	params.put("columnName", columnToCompare);
	// 	params.put("columnValue", columnValue);
	// 	params.put("updateColumn", columnToUpdate);
	// 	params.put("updateValue", updateValue);
	// 	params.put("userId", userID);

	// 	try {
	// 		ParseCloud.callFunction("insertUsersIntoEvent", params);
	// 		eventsParseListener.successful(null);
	// 	} catch (ParseException e) {
	// 		eventsParseListener.unsuccessful(e);
	// 	}
	// }

	// public void getAllEvents(String username, final GetAllEventsListener getAllEventsListener) {

	// 	ParseQuery<Attendee> query = ParseQuery.getQuery(Attendee.class);

	// 	// Get only the event this username is invited to
	// 	query.whereEqualTo(Attendee.eventAttendeeKey, username);

	// 	// Also get the Event object (relational data)
	// 	query.include(DbConfig.eventTable);

	// 	query.findInBackground(new FindCallback<Attendee>() {
	// 		public void done(List<Attendee> results, ParseException e) {
	// 			if (e == null) {
	// 				getAllEventsListener.successful(results);
	// 			} else {
	// 				getAllEventsListener.unsuccessful(e);
	// 			}
	// 		}
	// 	});
	// }
}
