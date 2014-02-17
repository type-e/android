package com.typee.typee.network.event;

import com.parse.ParseException;
import com.typee.typee.network.BaseParseListener;
import com.typee.typee.network.BaseParseService;
import java.util.*;
/**
 * Created by winsonlim on 20/1/14.
 */
public class EventsParseService extends BaseParseService {

	public void setEvent(String eventName, String eventDescription, String eventVenue, String eventTime, String username, final EventsParseListener eventsParseListener)
	{
        HashMap<String,String> eventDetails = new HashMap<String,String>();
        eventDetails.put("Event Name", eventName);
        eventDetails.put("Event Description", eventDescription);
        eventDetails.put("Event Venue", eventVenue);
        eventDetails.put("Event Time", eventTime);

        try {
            BaseParseService.getBaseParseService().setData("Ëvents", eventDetails);
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
        eventsParseListener.successful();
	}

    public void deleteEvent(String tableName, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener){
        try {
            BaseParseService.getBaseParseService().deleteData(tableName, columnToCompare, columnValue);
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
        eventsParseListener.successful();
    }

    public void updateEventDetail(String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener){
        try {
            BaseParseService.getBaseParseService().updateColumnValue(tableName, columnToUpdate, updateValue, columnToCompare, columnValue);
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
        eventsParseListener.successful();
    }
}
