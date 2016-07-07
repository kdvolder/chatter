package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("my")
@Component
public class MyProperties {

	/**
	 * The username the bot uses to send messages in the chat channel.
	 */
	private String user = "nobody";
	
	/**
	 * THe time the bot waits (in ms) between sending its 'ping' messages.
	 */
	private long interval = 1000;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
	
}
