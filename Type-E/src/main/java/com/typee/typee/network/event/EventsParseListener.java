package com.typee.typee.network.event;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by gengxian on 2/17/14.
 */
public interface EventsParseListener {
    public void successful();

    public void unsuccessful(ParseException e);

    public void getEventsDetailsSuccessful(List<ParseObject> resultsList);
}
