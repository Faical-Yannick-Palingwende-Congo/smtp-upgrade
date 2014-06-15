package com.origine.authenticity.service.rest.envelop.field;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payload")
public class ResponseBasicPayload {
	public String code;
	public String content;
	
	public ResponseBasicPayload() {

	}

	public ResponseBasicPayload(String code, String content) {
		super();
		this.code = code;
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public String getContent() {
		return content;
	}
}
