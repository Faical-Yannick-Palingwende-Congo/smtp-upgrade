package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="head")
public class DataHeadField {
	public String stamp;
	public String sender;
	public String source;
	public String au_key;
	
	
	public DataHeadField() {
		
	}

	public DataHeadField(String stamp, String sender, String source,
			String au_key) {
		this.stamp = stamp;
		this.sender = sender;
		this.source = source;
		this.au_key = au_key;
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
