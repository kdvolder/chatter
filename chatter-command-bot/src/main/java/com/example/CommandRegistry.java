package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandRegistry implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(CommandRegistry.class);
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	CommandBotProperties props;
	
	private Map<String, CommandHandler> commands = new HashMap<>();
	
	public CommandRegistry() {
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for (Entry<String, Class<? extends CommandHandler>> cmdDef : props.getCommands().entrySet()) {
			String name = cmdDef.getKey();
			Class<? extends CommandHandler> handlerClass = cmdDef.getValue();
			CommandHandler handler = getHandler(handlerClass);
			CommandHandler existing = commands.get(name);
			if (existing!=null) {
				log.warn("Multiple handlers defined for bot command '{}'", name);
				log.warn("Some handlers where ignored!", name);
			} else {
				commands.put(name, handler);
			}
		}
	}
	
	private CommandHandler getHandler(Class<? extends CommandHandler> handlerClass) {
		return appContext.getBean(handlerClass);
	}

	public CommandHandler get(String name) {
		return commands.get(name);
	}
	
}
