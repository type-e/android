package com.typee.typee.network.model;

/**
 * Created by winsonlim on 15/2/14.
 */
public class SMS {
	private String sender;
	private String message;

	public SMS() {
	}

	public SMS(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}


}
