package com.typee.typee.network.registration;

/**
 * Created by Winson Lim on 1/25/14.
 */
public interface RegistrationListener {

	public void registerSuccessful();

	public void registerUnsuccessful();

	public void setUserDetailsSuccessful();

	public void setUserDetailsUnsuccessful();
}
