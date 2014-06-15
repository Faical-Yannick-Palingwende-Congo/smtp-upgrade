package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="data")
public class DataField {
	public DataHeadField head;
	public String account;
	public String content;
	
	public DataField() {
		
	}

	public DataField(DataHeadField head, String account, String content) {
		super();
		this.head = head;
		this.account = account;
		this.content = content;
	}
	
	public DataHeadField getHead() {
		return head;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getContent() {
		return content;
	}
}
