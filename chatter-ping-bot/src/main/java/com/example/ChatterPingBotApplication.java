package com.example;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBinding(Source.class)
@EnableScheduling
@EnableConfigurationProperties(PingProperties.class)
public class ChatterPingBotApplication {
	
	private static final Logger log = LoggerFactory.getLogger(ChatterPingBotApplication.class);
	
	private String test;
	
	@Autowired
	Source channel;
	
	@Autowired
	TaskScheduler scheduler; 
	
	@Autowired
	PingProperties ping;

	private int counter;
	
	@PostConstruct
	void start() {
		log.debug("starting pingbot");

		scheduler.scheduleAtFixedRate(() -> {
			log.debug("sending message");

			channel.output().send(
					MessageBuilder.withPayload("["+ping.getUser()+"]: "+(counter++)).build());
		}, ping.getTime());
	}

	public static void main(String[] args) {
		SpringApplication.run(ChatterPingBotApplication.class, args);
	}
}
