package com.origine.authenticity.service.rest.entity;

public class HeadOutbox {
	public String id;
	public String stamp;
	public String destinator;
	public String destination;
	public String au_key;
	
	
	public HeadOutbox() {
		
	}

	public HeadOutbox(String id, String stamp, String destinator, String destination,
			String au_key) {
		super();
		this.id = id;
		this.stamp = stamp;
		this.destinator = destinator;
		this.destination = destination;
		this.au_key = au_key;
	}

	public String getId() {
		return id;
	}

	public String getStamp() {
		return stamp;
	}

	public String getDestinator() {
		return destinator;
	}

	public String getDestination() {
		return destination;
	}

	public String getAuKey() {
		return au_key;
	}
}
