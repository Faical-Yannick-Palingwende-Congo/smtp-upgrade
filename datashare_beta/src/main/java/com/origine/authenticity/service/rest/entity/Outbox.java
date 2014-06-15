package com.origine.authenticity.service.rest.entity;

public class Outbox {
	public String id;
	public String head_outbox;
	public String account;
	public String content;
	
	public Outbox() {
		
	}

	public Outbox(String id, String head_outbox, String account, String content) {
		super();
		this.id = id;
		this.head_outbox = head_outbox;
		this.account = account;
		this.content = content;
	}

	public String getId() {
		return id;
	}
	
	public String getHeadOutbox() {
		return head_outbox;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getContent() {
		return content;
	}
}
