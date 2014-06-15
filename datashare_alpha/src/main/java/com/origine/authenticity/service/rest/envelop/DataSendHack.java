package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.DataSendHackPayload;

@XmlRootElement
public class DataSendHack extends RequestHead{
	public DataSendHackPayload payload;
	
	public DataSendHack() {
		super();
	}

	public DataSendHack(String stamp, DataSendHackPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public DataSendHackPayload getPayload() {
		return payload;
	}
}
