package com.typee.typee.network.event;

import com.typee.typee.network.BaseParseService;

/**
 * Created by winsonlim on 20/1/14.
 */
public class EventsParseService extends BaseParseService {

	public boolean setEvent(String eventName, String eventDescription, String eventVenue, String eventTime)
	{
		HashMap<String,String> eventDetails = new HashMap<String,String>();
		eventDetails.put("eventName", eventName);
		eventDetails.put("eventDescription", eventDescription);
		eventDetails.put("eventVenue", eventVenue);
		eventDetails.put("eventTime", eventTime);

		return setData("Ëvents", eventDetails);
	}
}
