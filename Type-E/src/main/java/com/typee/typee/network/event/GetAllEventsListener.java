package com.typee.typee.network.event;

import com.typee.typee.network.base.ErrorListener;
import com.typee.typee.network.model.Attendee;

import java.util.List;

/**
 * Created by gengxian on 2/17/14.
 */
public interface GetAllEventsListener extends ErrorListener {

	public void successful(List<Attendee> data);

}
