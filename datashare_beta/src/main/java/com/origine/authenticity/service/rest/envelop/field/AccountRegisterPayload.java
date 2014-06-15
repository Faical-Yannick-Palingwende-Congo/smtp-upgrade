package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payload") 
public class AccountRegisterPayload{
	public String email;
	public String password;
	
	public boolean sanity() {
		return email != null && password != null;
	}
	
	public AccountRegisterPayload() {
		
	}

	public AccountRegisterPayload(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
