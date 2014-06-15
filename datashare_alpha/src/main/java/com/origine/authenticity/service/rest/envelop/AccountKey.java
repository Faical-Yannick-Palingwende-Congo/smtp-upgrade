package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.AccountKeyPayload;

@XmlRootElement
public class AccountKey extends RequestHead{
	public AccountKeyPayload payload;
	
	public AccountKey() {
		super();
	}

	public AccountKey(String stamp, AccountKeyPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public AccountKeyPayload getPayload() {
		return payload;
	}
}
