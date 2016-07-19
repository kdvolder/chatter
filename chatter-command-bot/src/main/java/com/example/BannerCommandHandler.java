package com.example;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.lalyos.jfiglet.FigletFont;

@Component
public class BannerCommandHandler implements CommandHandler {

	private static final Logger log = LoggerFactory.getLogger(BannerCommandHandler.class);

	@Autowired
	private FigletFont figlet;
	
	@Override
	public void handle(Command cmd, Processor channel) {
		try {
			String banner = figlet.convert(cmd.getArgument());
			String[] lines = banner.split("\n");
			log.debug("banner converted from '{}'", cmd.getArgument());
			for (String string : lines) {
				if (StringUtils.hasText(string)) {
					channel.output().send(MessageBuilder.withPayload(string).build());
				}
			}
		} catch (IOException e) {
			log.error("Error handling command: "+cmd, e);
		}
	}

}
