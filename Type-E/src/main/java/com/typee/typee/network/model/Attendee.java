package com.typee.typee.network.model;

/**
 * Created by winsonlim on 29/4/14.
 */

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.typee.typee.config.DbConfig;
// import com.typee.typee.config.DbConfig;

@ParseClassName(DbConfig.attendeeTable)
public class Attendee extends ParseObject {
	public static final String attendeeEventKey = "AttendeeEvent";
	public static final String attendeeTaskKey = "AttendeeTask";
	public static final String attendeeSplittedBillKey = "AttendeeSplittedBill";
	public static final String attendeeNameKey = "AttendeeName";
	public static final String attendeeLocation = "AttendeeLocation";
	public static final String attendeeStatusKey = "AttendeeStatus";

	// private static final String attendeeTableName = "";

	// public Attendee(String eventAttendeeTableName) {
	// 	this.eventAttendeeTableName = eventAtteedeeTableName;
	// }

	public String getAttendeeEvent() {
		return getString(attendeeEventKey);
	}

	public String getAttendeeTask() {
		return getString(attendeeTaskKey);
	}

	public String getAttendeeSplittedBill() {
		return getString(attendeeSplittedBillKey);
	}

	public String getAttendeeName() {
		return getString(attendeeNameKey);
	}

    //TODO: no attendeeLocationKey
//	public String getAttendeeLocation() {
//		return getString(attendeeLocationKey);
//	}

	public String getAttendeeStatus() {
		return getString(attendeeStatusKey);
	}



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
