package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payload")
public class DataHeadPayload {
	public String stamp;
	public String sender;
	public String au_key;
	
	public boolean sanity() {
		return stamp != null && sender != null;
	}
	
	public DataHeadPayload() {
		
	}

	public DataHeadPayload(String stamp, String sender, String au_key) {
		super();
		this.stamp = stamp;
		this.sender = sender;
		this.au_key = au_key;
	}
	
	public String getStamp() {
		return stamp;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getAuKey() {
		return au_key;
	}
}
