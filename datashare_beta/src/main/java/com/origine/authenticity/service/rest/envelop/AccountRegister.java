package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

import com.origine.authenticity.service.rest.envelop.field.AccountRegisterPayload;

@XmlRootElement
public class AccountRegister extends RequestHead{
	public AccountRegisterPayload payload;
	
	public AccountRegister() {
		super();
	}

	public AccountRegister(String stamp, AccountRegisterPayload payload) {
		super(stamp);
		this.payload = payload;
	}
	
	public AccountRegisterPayload getPayload() {
		return payload;
	}
}
