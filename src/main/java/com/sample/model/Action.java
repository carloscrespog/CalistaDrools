package com.sample.model;

public class Action {
	private String description;
	public Action(String description, String title) {
		super();
		this.description = description;
		this.title = title;
	}
	public Action(String description) {
		super();
		this.description = description;
		this.title = "Action Title";
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String title;
}
