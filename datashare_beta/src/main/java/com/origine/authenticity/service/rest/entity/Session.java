package com.origine.authenticity.service.rest.entity;

public class Session {
	public String id;
	public String account;
	public String sess_key;
	public String state;
	public String stamp;
	
	public Session() {
		
	}

	public Session(String id, String account, String sess_key, String state,
			String stamp) {
		super();
		this.id = id;
		this.account = account;
		this.sess_key = sess_key;
		this.state = state;
		this.stamp = stamp;
	}

	public String getId() {
		return id;
	}

	public String getAccount() {
		return account;
	}

	public String getSessKey() {
		return sess_key;
	}

	public String getState() {
		return state;
	}
	
	public String getStamp() {
		return stamp;
	}
}
