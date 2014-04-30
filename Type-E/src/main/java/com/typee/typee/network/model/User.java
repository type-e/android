package com.typee.typee.network.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by winsonlim on 28/4/14.
 */
@ParseClassName("User")
public class User extends ParseObject {
	public static final String usernameKey = "username";
	public static final String createdAtKey = "createdAt";
	public static final String updatedAtKey = "updatedAt";

	public String getUsername() {
		return getString(usernameKey);
	}

	public void setUsername(String username) {
		put(usernameKey, username);
	}

	public java.util.Date getCreatedAt() {
		return getDate(createdAtKey);
	}

	public void setCreatedAt(String createdAt) {
		put(createdAtKey, createdAt);
	}

	public java.util.Date getUpdatedAt() {
		return getDate(updatedAtKey);
	}

	public void setUpdatedAt(String updatedAt) {
		put(updatedAtKey, updatedAt);
	}

}
