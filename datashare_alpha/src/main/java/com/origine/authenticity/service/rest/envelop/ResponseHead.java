package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseHead {
	public String stamp;
	
	public ResponseHead() {

	}
	
	public ResponseHead(String stamp) {
		super();
		this.stamp = stamp;
	}

	public String getStamp() {
		return stamp;
	}
}
