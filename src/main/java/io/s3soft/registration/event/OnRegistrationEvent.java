package io.s3soft.registration.event;


import org.springframework.context.ApplicationEvent;

import io.s3soft.registration.model.User;

public class OnRegistrationEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String appUrl;

	public OnRegistrationEvent(User user,String appUrl) {
		super(user);
		this.user = user;
		this.appUrl = appUrl;
		System.out.println("event published");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	

}
