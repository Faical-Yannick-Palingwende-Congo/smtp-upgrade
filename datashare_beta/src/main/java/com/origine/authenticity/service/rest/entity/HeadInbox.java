package com.origine.authenticity.service.rest.entity;

public class HeadInbox {
	public String id;
	public String stamp;
	public String sender;
	public String source;
	public String au_key;
	
	
	public HeadInbox() {
		
	}

	public HeadInbox(String id, String stamp, String sender, String source,
			String au_key) {
		super();
		this.id = id;
		this.stamp = stamp;
		this.sender = sender;
		this.source = source;
		this.au_key = au_key;
	}

	public String getId() {
		return id;
	}

	public String getStamp() {
		return stamp;
	}

	public String getSender() {
		return sender;
	}

	public String getSource() {
		return source;
	}

	public String getAuKey() {
		return au_key;
	}
}
