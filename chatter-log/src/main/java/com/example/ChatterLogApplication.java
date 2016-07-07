package com.example;

import javax.management.MBeanServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.RegistrationPolicy;
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
	
	@Bean
    public IntegrationMBeanExporter integrationMbeanExporter(
            @Value("spring.jmx.default-domain") String domain, 
            MBeanServer mBeanServer) {
        IntegrationMBeanExporter exporter = new IntegrationMBeanExporter();
        exporter.setDefaultDomain(domain);
        exporter.setServer(mBeanServer);
        return exporter;
	}
	
	@Bean
	@Primary
	public AnnotationMBeanExporter mbeanExporter(ObjectNamingStrategy namingStrategy, MBeanServer mBeanServer) {
	        AnnotationMBeanExporter exporter = new AnnotationMBeanExporter();
	        exporter.setRegistrationPolicy(RegistrationPolicy.FAIL_ON_EXISTING);
	        exporter.setNamingStrategy(namingStrategy);
	        exporter.setServer(mBeanServer);
	        return exporter;
	}	
	
}
