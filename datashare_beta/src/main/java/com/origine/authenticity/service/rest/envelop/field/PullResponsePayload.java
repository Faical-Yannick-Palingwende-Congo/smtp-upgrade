package com.origine.authenticity.service.rest.envelop.field;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payload")
public class PullResponsePayload {
	public String code;
	public LinkedList<DataField> datas;
	
	public PullResponsePayload() {

	}

	public PullResponsePayload(String code, LinkedList<DataField> datas) {
		super();
		this.code = code;
		this.datas = datas;
	}
	
	public String getCode() {
		return code;
	}

	public LinkedList<DataField> getDatas() {
		return datas;
	}
}
