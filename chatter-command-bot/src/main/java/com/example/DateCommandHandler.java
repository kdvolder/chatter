package com.example;

import java.util.Date;

import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.example.CommandHandler;

@Component
public class DateCommandHandler implements CommandHandler {

	@Override
	public void handle(Command cmd, Processor channel) {
		channel.output().send(MessageBuilder.withPayload(new Date().toString()).build());
	}

}
