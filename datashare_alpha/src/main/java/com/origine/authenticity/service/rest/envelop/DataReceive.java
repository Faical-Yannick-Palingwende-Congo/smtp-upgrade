package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import net.sf.json.JSONObject;

import com.origine.authenticity.service.rest.envelop.field.DataReceivePayload;

@XmlRootElement
public class DataReceive extends RequestHead{
	public DataReceivePayload payload;
	
	public DataReceive() {
		super();
	}

	public DataReceive(String stamp, DataReceivePayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public DataReceivePayload getPayload() {
		return payload;
	}
	
	public JSONObject toJson(){
		JSONObject object = new JSONObject();
		object.put("stamp", stamp);
		object.put("payload", payload);
		return object;
	}
}
