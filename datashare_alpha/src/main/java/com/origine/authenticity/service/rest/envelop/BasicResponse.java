package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.ResponseBasicPayload;

@XmlRootElement
public class BasicResponse extends RequestHead{
	public ResponseBasicPayload payload;
	
	public BasicResponse() {
		super();
	}

	public BasicResponse(String stamp, ResponseBasicPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public ResponseBasicPayload getPayload() {
		return payload;
	}
}
