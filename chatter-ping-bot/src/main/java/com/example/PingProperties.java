package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;

@ConfigurationProperties("chatter.ping")
public class PingProperties {

	/**
	 * THe name used iin the message
	 */
	private String user = "nobody";
	/**
	 * The time between msgs in ms.
	 */
	private long interval = 3000;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@DeprecatedConfigurationProperty(
			reason="bad name",
			replacement="chatter.ping.interval")
	public long getTime() {
		return getInterval();
	}
	public void setTime(long time) {
		this.setInterval(time);
	}
	public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}

}
