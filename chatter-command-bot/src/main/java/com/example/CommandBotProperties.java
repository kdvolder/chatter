package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("chatter.bot")
public class CommandBotProperties {

	private Map<String, Class<? extends CommandHandler>> commands = new HashMap<>();

	public Map<String, Class<? extends CommandHandler>> getCommands() {
		return commands;
	}

}
