package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.DataSendTrustPayload;

@XmlRootElement
public class DataSendTrust extends RequestHead{
	public DataSendTrustPayload payload;
	
	public DataSendTrust() {
		super();
	}

	public DataSendTrust(String stamp, DataSendTrustPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public DataSendTrustPayload getPayload() {
		return payload;
	}
}
