package com.typee.typee.network.event;

import com.parse.ParseException;

/**
 * Created by gengxian on 2/17/14.
 */
public interface EventsParseListener {
    public void successful();

    public void unsuccessful(ParseException e);
}
