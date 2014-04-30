package com.typee.typee.service;

/**
 * Created by winsonlim on 25/4/14.
 */

public interface DataServiceListener {

	public void onDataReceived(Object data);

	public void onDataError(Object error);

}