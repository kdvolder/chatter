package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.integration.support.MessageBuilder;

/**
 * Chatter bot application. A stupid little 'bot' which connects to our 'chat' channel
 * and sends a numbered message every second.
 * 
 * @author Kris De Volder
 */
@SpringBootApplication
@EnableBinding(Source.class)
public class ChatterBotApplication implements CommandLineRunner {
	
	@Autowired
	Source channel;

	@Autowired
	private MyProperties prop;
	
	public static void main(String[] args) {
		SpringApplication.run(ChatterBotApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		int i = 0;
		while (true) {
			Thread.sleep(prop.getInterval());
			send("This is message #"+(++i));
		}
	}

	private void send(String msg) {
		channel.output().send(MessageBuilder.withPayload(msg).build());
	}
	
	
}
