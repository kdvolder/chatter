package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.stereotype.Component;

@ConfigurationProperties("chatter.ping")
@Component
public class PingBotProperties {

	/**
	 * The username the bot uses to send messages in the chat channel.
	 */
	private String user = "ping";
	
	/**
	 * The time the bot waits (in ms) between sending its 'ping' messages.
	 */
	private long interval = 3000;
	
	/**
	 * Message format for the 'ping' message. Use ${u} for the user name, ${c} for
	 * the message counter.
	 */
	private String messageFormat = "[${u}]: ${c}";

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

	@DeprecatedConfigurationProperty(reason="We picked a bad name", replacement="chatter.ping.message-format")
	public String getMessage() {
		return getMessageFormat();
	}

	public void setMessage(String message) {
		this.setMessageFormat(message);
	}

	public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}	
}
