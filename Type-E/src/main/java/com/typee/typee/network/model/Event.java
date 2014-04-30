package com.typee.typee.network.model;

import com.parse.ParseClassName;
import com.typee.typee.config.DbConfig;
import com.typee.typee.network.model.util.DateTime;

/**
 * Created by winsonlim on 28/4/14.
 */
@ParseClassName(DbConfig.eventTable)
public class Event extends User {
	public static final String eventNameKey = "eventName";
	public static final String eventDescriptionKey = "eventDescription";
	public static final String eventVenueKey = "eventVenue";
	public static final String eventStartDateTimeKey = "eventStartDateTime";

	private DateTime eventDateTime;

	public DateTime getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(DateTime eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public String getEventName() {
		return getString(eventNameKey);
	}

	public void setEventName(String eventName) {
		put(eventNameKey, eventName);
	}

	public String getEventDescription() {
		return getString(eventDescriptionKey);
	}

	public void setEventDescription(String eventDescription) {
		put(eventDescriptionKey, eventDescription);
	}

	public String getEventTime() {
		return getString(eventStartDateTimeKey);
	}

	public void setEventTime(String eventTime) {
		put(eventStartDateTimeKey, eventTime);
	}

	public String getEventVenue() {
		return getString(eventVenueKey);
	}

	public void setEventVenue(String eventVenue) {
		put(eventVenueKey, eventVenue);
	}
}