package com.example;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("chatter.bot")
public class CommandBotProperties {

	private Map<String, Class<? extends CommandHandler>> commands = new HashMap<>();
	private Resource figletFont = new ClassPathResource("standard.flf");

	public Map<String, Class<? extends CommandHandler>> getCommands() {
		return commands;
	}

	public Resource getFigletFont() {
		return figletFont;
	}

	public void setFigletFont(Resource figletFont) {
		this.figletFont = figletFont;
	}

}
