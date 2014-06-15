package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.PullResponsePayload;

@XmlRootElement
public class PullResponse extends RequestHead{
	public PullResponsePayload payload;
	
	public PullResponse() {
		super();
	}

	public PullResponse(String stamp, PullResponsePayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public PullResponsePayload getPayload() {
		return payload;
	}
}
