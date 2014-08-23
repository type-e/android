package com.typee.typee.network.model;

import com.parse.ParseClassName;
import com.typee.typee.config.DbConfig;
import com.typee.typee.network.model.util.DateTime;

/**
 * Created by winsonlim on 28/4/14.
 */
@ParseClassName(DbConfig.eventTable)
public class Event extends User {
	public static final String eventNameKey = "EventName";
	public static final String eventDescriptionKey = "EventDescription";
	public static final String eventVenueKey = "EventVenue";
	public static final String eventNoteKey = "EventNote";
	public static final String eventLocationKey = "EventLocation";
	public static final String eventRecurrenceKey = "EventRecurrence";
	public static final String eventStartDateTimeKey = "EventStartDateTime";
	public static final String eventEndDateTimeKey = "EventEndDateTime";

	private DateTime eventDateTime;

	public DateTime getEventStartDateTime() {
		return eventStartDateTimeKey;
	}

	public void setEventStartDateTime(DateTime eventStartDateTimeKey) {
		this.eventStartDateTimeKey = eventStartDateTimeKey;
	}

	public DateTime getEventEndDateTime() {
		return eventEndDateTimeKey;
	}

	public void setEventEndDateTime(DateTime eventEndDateTimeKey) {
		this.eventEndDateTimeKey = eventEndDateTimeKey;
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

	public String getEventNote() {
		return getString(eventNoteKey);
	}

	public void setEventNote(String note) {
		put(eventNoteKey, note);
	}

	public String getEventVenue() {
		return getString(eventVenueKey);
	}

	public void setEventVenue(String eventVenue) {
		put(eventVenueKey, eventVenue);
	}

	public String getEventRecurrence() {
		return getString(eventRecurrenceKey);
	}

	public void setEventRecurrence(String eventRecurrenceKey) {
		put(eventRecurrenceKey, eventRecurrence);
	}

	public String getEventLocation() {
		return getString(eventRecurrenceKey);
	}

	public void setEventLocation(String eventLocationKey) {
		put(eventLocationKey, eventLocation);
	}
}