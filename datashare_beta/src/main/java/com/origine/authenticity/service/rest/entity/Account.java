package com.origine.authenticity.service.rest.entity;

public class Account {
	public String id;
	public String email;
	public String password;
	public String acc_key;
	
	public Account() {
		
	}

	public Account(String id, String email, String password, String acc_key) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.acc_key = acc_key;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public String getAccKey() {
		return acc_key;
	}
}
