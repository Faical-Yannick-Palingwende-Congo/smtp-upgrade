package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payload")
public class DataSendHackPayload {
	public String stamp;
	public String sender;
	public String source;
	public String destinator;
	public String destination;
	public String content;
	public String au_key;
	
	public boolean sanity() {
		return stamp != null && sender != null  && source != null && destinator != null  && destination != null ;
	}
	
	public DataSendHackPayload() {
		
	}

	public DataSendHackPayload(String stamp, String sender, String source, String destinator, String destination, String content, String au_key) {
		super();
		this.stamp = stamp;
		this.sender = sender;
		this.source = source;
		this.destinator = destinator;
		this.destination = destination;
		this.content = content;
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
	
	public String getDestinator() {
		return destinator;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getAuKey() {
		return au_key;
	}
}
