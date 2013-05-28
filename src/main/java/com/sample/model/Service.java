package com.sample.model;

public class Service {
	private String title;
	private String description;
	private String logo;
	private String pageURL;
	private Action action;
	private Event event;
	public Service(String title, String description, String logo,
			String pageURL, Action action, Event event) {
		super();
		this.title = title;
		this.description = description;
		this.logo = logo;
		this.pageURL = pageURL;
		this.action = action;
		this.event = event;
	}
	public Service(String title){
		this.title = title;
		this.description = "my description";
		this.logo = "my logo";
		this.pageURL = "my pageURL";
		this.action = null;
		this.event = null;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
}
