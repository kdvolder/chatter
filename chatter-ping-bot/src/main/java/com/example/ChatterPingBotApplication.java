package com.example;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * Chatter bot application. A stupid little 'bot' which connects to our 'chat' channel
 * and sends a numbered message every second.
 * 
 * @author Kris De Volder
 */
@SpringBootApplication
@EnableBinding(Source.class)
@EnableScheduling
public class ChatterPingBotApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(ChatterPingBotApplication.class);
	
	@Autowired
	Source channel;

	@Autowired
	TaskScheduler scheduler;
	
	@Autowired
	private PingBotProperties config;
	
	private PropertyPlaceholderHelper formatter = new PropertyPlaceholderHelper("${", "}");
	
	public static void main(String[] args) {
		SpringApplication.run(ChatterPingBotApplication.class, args);
	}

	int i;

	@Override
	public void run(String... arg0) throws Exception {
		log.info("Initializing bot: {}", config.getUser());

		scheduler.scheduleAtFixedRate(() -> {
			String formatted = formatter.replacePlaceholders(config.getMessage(), messageParameters());
			log.debug("fomatted message = {}", formatted);
			send(formatted);
		}, config.getInterval());
	}

	private Properties messageParameters() {
		Properties params = new Properties();
		params.put("u", config.getUser());
		params.put("c",  ++i+"");
		return params;
	}

	private void send(String msg) {
		channel.output().send(MessageBuilder.withPayload(msg).build());
	}
	
}
