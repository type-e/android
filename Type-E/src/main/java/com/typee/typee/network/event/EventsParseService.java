package com.typee.typee.network.event;

import com.parse.ParseCloud;
import com.parse.ParseException;
import com.typee.typee.dbTableDetails.Dbconfig;
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
            BaseParseService.getBaseParseService().setData(Dbconfig.eventTable, eventDetails);
            eventsParseListener.successful();
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
	}

    public void deleteEvent(String tableName, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener){
        try {
            BaseParseService.getBaseParseService().deleteData(Dbconfig.eventTable, columnToCompare, columnValue);
            eventsParseListener.successful();
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
    }

    public void updateEventDetail(String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener){
        try {
            BaseParseService.getBaseParseService().updateColumnValue(Dbconfig.eventTable, columnToUpdate, updateValue, columnToCompare, columnValue);
            eventsParseListener.successful();
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
    }

    public void addUsersToEvent(String userID, String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue, final EventsParseListener eventsParseListener){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tableName", tableName);
        params.put("columnName", columnToCompare);
        params.put("columnValue", columnValue);
        params.put("updateColumn", columnToUpdate);
        params.put("updateValue", updateValue);
        params.put("userId", userID);

        try {
            ParseCloud.callFunction("insertUsersIntoEvent", params);
            eventsParseListener.successful();
        } catch (ParseException e) {
            eventsParseListener.unsuccessful(e);
        }
    }
}
