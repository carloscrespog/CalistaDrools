package com.sample.model;

public class User {
	private String accountName;
	private Service service;
	public User(String accountName, Service service) {
		super();
		this.accountName = accountName;
		this.service = service;
	}
	public User(String accountName) {
		super();
		this.accountName = accountName;
		this.service = null;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
}
