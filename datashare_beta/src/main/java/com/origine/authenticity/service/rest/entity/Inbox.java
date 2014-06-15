package com.origine.authenticity.service.rest.entity;

public class Inbox {
	public String id;
	public String head_inbox;
	public String account;
	public String content;
	
	public Inbox() {
		
	}

	public Inbox(String id, String head_inbox, String account, String content) {
		super();
		this.id = id;
		this.head_inbox = head_inbox;
		this.account = account;
		this.content = content;
	}

	public String getId() {
		return id;
	}
	
	public String getHeadInbox() {
		return head_inbox;
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getContent() {
		return content;
	}
}
