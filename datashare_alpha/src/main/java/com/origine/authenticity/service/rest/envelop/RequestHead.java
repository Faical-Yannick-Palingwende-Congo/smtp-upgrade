package com.origine.authenticity.service.rest.envelop;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestHead {
	public String stamp;
	
	public RequestHead() {

	}
	
	public RequestHead(String stamp) {
		super();
		this.stamp = stamp;
	}

	public String getStamp() {
		return stamp;
	}
}
