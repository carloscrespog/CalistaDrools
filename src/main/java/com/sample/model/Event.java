package com.sample.model;

public class Event {
	private String description;
	private String title;
	private int value;
	public Event(String title, String description) {
		super();
		this.description = description;
		this.title = title;
	}
	public Event(String event, int value) {
		this.value=value;
		this.description = "my description";
		this.title = event;
	}
	public Event(String title) {
		super();
		this.description = title;
		this.title = "Event title";
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
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
}
