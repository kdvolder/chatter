package com.example;

import org.springframework.cloud.stream.messaging.Processor;

public interface CommandHandler {

	/**
	 * Called when a message with a command trigger is detected in a received message.
	 * A typical implementation will use the data in the command to do some processing 
	 * and send the result to the 'output' of the channel on which the command was
	 * received.
	 * 
	 * @param cmd Parsed command data.
	 * @param channel The channel on which the message was received.
	 */
	void handle(Command cmd, Processor channel);
	
}
