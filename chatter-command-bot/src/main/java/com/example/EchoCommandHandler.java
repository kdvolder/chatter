package com.example;

import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.example.CommandHandler;

@Component
public class EchoCommandHandler implements CommandHandler {
	
	@Override
	public void handle(Command cmd, Processor channel) {
		channel.output().send(MessageBuilder.withPayload(cmd.getArgument()).build());
	}

}
