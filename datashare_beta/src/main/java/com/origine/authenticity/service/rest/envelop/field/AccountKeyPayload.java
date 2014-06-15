package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payload")
public class AccountKeyPayload {
	public String key;
	
	public boolean sanity() {
		return key != null;
	}
	
	public AccountKeyPayload() {
		
	}

	public AccountKeyPayload(String key) {
		super();
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
