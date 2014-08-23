package com.typee.typee.network.attendee;

import com.typee.typee.network.base.ErrorListener;

/**
 * Created by gengxian on 2/17/14.
 */
public interface AttendeeParseListener extends ErrorListener {

	public void successful(Object data);

}