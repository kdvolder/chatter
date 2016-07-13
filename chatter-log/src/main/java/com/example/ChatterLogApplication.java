package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * A 'logger' application that connects to the 'chat' channel and simply logs all
 * chat messages as info messages.
 */
@SpringBootApplication
@EnableBinding(Sink.class)
public class ChatterLogApplication {

	private static final Logger log = LoggerFactory.getLogger(ChatterLogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatterLogApplication.class, args);
	}
	
	@StreamListener("input")
	public void logChatMessage(Message<String> msg) {
		log.info(msg.getPayload());
	}
		
}
