package com.typee.typee.network.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.typee.typee.config.DbConfig;

import java.util.Date;

/**
 * Created by winsonlim on 28/4/14.
 */
@ParseClassName(DbConfig.eventTable)
public class Event extends User {
	public static final String eventNameKey = "eventName";
	public static final String eventDescriptionKey = "eventDescription";
	public static final String eventVenueKey = "eventVenue";
	public static final String eventNoteKey = "eventNote";
	public static final String eventLocation = "eventLocation";
	public static final String eventRecurrenceKey = "eventRecurrence";
	public static final String eventStartDateTimeKey = "eventStartDateTime";
	public static final String eventEndDateTimeKey = "eventEndDateTime";
	public static final String eventLocationKey = "eventLocationKey";

	public Date getEventStartDateTime() {
		return getDate(eventStartDateTimeKey);
	}

	public void setEventStartDateTime(Date eventStartDateTimeKey) {
		put(this.eventStartDateTimeKey, eventStartDateTimeKey);
	}

	public Date getEventEndDateTime() {
		return getDate(eventEndDateTimeKey);
	}

	public void setEventEndDateTime(Date eventEndDateTimeKey) {
		put(this.eventEndDateTimeKey, eventEndDateTimeKey);
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

	public void setEventRecurrence(String eventRecurrence) {
		put(eventRecurrenceKey, eventRecurrence);
	}

	public String getEventLocation() {
		return getString(eventLocation);
	}

	public void setEventLocation(ParseGeoPoint eventLocation) {
		put(eventLocationKey, eventLocation);
	}
}