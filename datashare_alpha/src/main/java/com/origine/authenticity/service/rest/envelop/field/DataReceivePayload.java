package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

import net.sf.json.JSONObject;

@XmlRootElement(name="payload")
public class DataReceivePayload {
	public String stamp;
	public String sender;
	public String source;
	public String destinator;
	public String content;
	public String au_key;
	
	public boolean sanity() {
		return stamp != null && sender != null  && source != null && destinator != null ;
	}
	
	public DataReceivePayload() {
		
	}

	public DataReceivePayload(String stamp, String sender, String source, String destinator, String content, String au_key) {
		super();
		this.stamp = stamp;
		this.sender = sender;
		this.source = source;
		this.destinator = destinator;
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
	
	public String getContent() {
		return content;
	}
	
	public String getAuKey() {
		return au_key;
	}
	
	public JSONObject toJson(){
		JSONObject object = new JSONObject();
		object.put("stamp", stamp);
		object.put("sender", sender);
		object.put("source", source);
		object.put("destinator", destinator);
		object.put("content", content);
		object.put("au_key", au_key);
		return object;
	}
}
