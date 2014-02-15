package com.typee.typee.network.registration;

/**
 * Created by Winson Lim on 1/25/14.
 */
public interface TokenSentListener {

	public void tokenSentSuccessful(String token);

	public void tokenSentUnsuccessful();
}
