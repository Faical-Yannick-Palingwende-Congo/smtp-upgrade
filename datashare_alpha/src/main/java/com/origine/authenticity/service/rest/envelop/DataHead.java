package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.DataHeadPayload;

@XmlRootElement
public class DataHead extends RequestHead{
	public DataHeadPayload payload;
	
	public DataHead() {
		super();
	}

	public DataHead(String stamp, DataHeadPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public DataHeadPayload getPayload() {
		return payload;
	}
}
