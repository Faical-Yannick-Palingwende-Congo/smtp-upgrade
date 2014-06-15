package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.AccountLoginPayload;

@XmlRootElement
public class AccountLogin extends RequestHead{
	public AccountLoginPayload payload;
	
	public AccountLogin() {
		super();
	}

	public AccountLogin(String stamp, AccountLoginPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public AccountLoginPayload getPayload() {
		return payload;
	}
}
