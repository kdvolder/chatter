package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.github.lalyos.jfiglet.FigletFont;

@SpringBootApplication
@EnableBinding(Processor.class)
public class ChatterCommandBotApplication {
	
	private static final Logger log = LoggerFactory.getLogger(ChatterCommandBotApplication.class);

	private static final Pattern CMD = Pattern.compile("\\!([a-zA-Z]*)(.*)");
	
	@Autowired
	Processor channel;
	
	@Autowired
	CommandRegistry handlers;
	
	@Bean
	FigletFont figletFont(CommandBotProperties props) throws IOException {
		Resource fontResource = props.getFigletFont();
		try (InputStream stream = fontResource.getInputStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			int max = 10;
			while (max-->0 && null!=(line = reader.readLine())) {
				log.debug(line);
			}
		}
		try (InputStream stream = fontResource.getInputStream()) {
			return new FigletFont(stream);
		}
	}
	
	@StreamListener(Processor.INPUT)
	void dispatchCommands(String msg) {
		log.debug("message received '"+msg+"'");
		Command cmd = parse(msg);
		if (cmd!=null) {
			log.debug("command pattern detected: {}", cmd);
			CommandHandler handler = handlers.get(cmd.getName());
			if (handler!=null) {
				log.debug("Command {} handling with {}", cmd, handler);
				handler.handle(cmd, channel);
			} else {
				log.debug("Command {} ignored: no handler", cmd);
			}
		}
	}

	private Command parse(String msg) {
		Matcher matcher = CMD.matcher(msg);
		boolean isCommand = matcher.matches();
		if (isCommand) {
			return new Command(msg, matcher.group(1), matcher.group(2).trim());
		}
		return null;
	}

	public static void main(String[] args) {
		SpringApplication.run(ChatterCommandBotApplication.class, args);
		
	}
}
