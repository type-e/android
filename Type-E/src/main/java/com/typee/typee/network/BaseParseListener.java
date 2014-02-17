package com.typee.typee.network;
import com.parse.ParseException;

public interface BaseParseListener {

    public void successful();

    public void unsuccessful(ParseException e);
}
