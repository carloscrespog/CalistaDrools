package com.sample.model;

public class EweRule {
	
	private String title;
	private String description;
	private int timesUsed;
	private String pageURL;
	public EweRule(String title, String description, int timesUsed,
			String pageURL, User user) {
		super();
		this.title = title;
		this.description = description;
		this.timesUsed = timesUsed;
		this.pageURL = pageURL;
		this.user = user;
	}
	public EweRule(String title){
		this.title = title;
		this.description = "my description";
		this.timesUsed = 0;
		this.pageURL = "my pageURL";
		this.user = null;
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
	public int getTimesUsed() {
		return timesUsed;
	}
	public void setTimesUsed(int timesUsed) {
		this.timesUsed = timesUsed;
	}
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private User user;
}
