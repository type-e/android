package com.typee.typee.network.model;

/**
 * Created by winsonlim on 29/4/14.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.typee.typee.config.DbConfig;

@ParseClassName(DbConfig.attendeeTable)
public class Attendee extends ParseObject {
	public static final String eventAttendeeKey = "eventAttendee";

	public String getEventAttendee() {
		return getString(eventAttendeeKey);
	}

	public void setEventAttendee(String eventAttendee) {
		put(eventAttendeeKey, eventAttendee);
	}

	public Event getEvent() {
		return (Event) getParseObject(DbConfig.eventTable);
	}

	public void setEvent(Event eventObjectId) {
		put(DbConfig.eventTable, eventObjectId);
	}
}