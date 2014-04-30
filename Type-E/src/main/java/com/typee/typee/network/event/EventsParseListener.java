package com.typee.typee.network.event;

import com.typee.typee.network.base.ErrorListener;

/**
 * Created by gengxian on 2/17/14.
 */
public interface EventsParseListener extends ErrorListener {

	public void successful(Object data);

}
