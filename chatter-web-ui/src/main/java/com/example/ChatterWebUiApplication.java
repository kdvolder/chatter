package com.example;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@EnableWebSocket
@Controller
@EnableBinding(Processor.class)
public class ChatterWebUiApplication implements WebSocketConfigurer {

	private static final Logger log = LoggerFactory.getLogger(ChatterWebUiApplication.class);
	
	private Set<WebSocketSession> ws_sessions = new HashSet<>();

	@Autowired
	private Processor broker;
	
	public static void main(String[] args) {
		SpringApplication.run(ChatterWebUiApplication.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(wsToBrokerRelay(), "/websocket").withSockJS();
	}
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	/**
	 * WebSocketHandler which receives messages from a websocket and forwards them to a
	 * spring-cloud-stream.
	 */
	@Bean
	public WebSocketHandler wsToBrokerRelay() {
		return new TextWebSocketHandler() {

			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				ws_sessions.add(session);
				log.info("Websocket connection OPENED in: "+this);
				log.info("Number of active sessions = {}", ws_sessions.size());
			}

			@Override
			protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
				broker.output().send(MessageBuilder.withPayload(message.getPayload()).build());
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
				log.info("Websocket connection CLOSED in: "+this);
				ws_sessions.remove(session);
				log.info("Number of active sessions = {}", ws_sessions.size());
			}
			
			@Override
			public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
				log.error("Websocket trasnport error: ", exception);
				ws_sessions.remove(session);
			}
		};
	}
	
	@StreamListener(Sink.INPUT)
	public void receivedFromBroker(@Payload String msg) {
		synchronized (ws_sessions) {
			for (WebSocketSession ws : ws_sessions) {
				try {
					if (ws.isOpen()) {
						ws.sendMessage(new TextMessage(msg));
					}
				} catch (Exception e) {
					log.error("Error forwarding message to ws session", e);
				}
			}
		}
	}
}
