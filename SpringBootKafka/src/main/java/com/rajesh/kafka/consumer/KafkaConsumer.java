package com.rajesh.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rajesh.kafka.model.User;

@Service
public class KafkaConsumer {

	private static final Logger logger=LoggerFactory.getLogger(KafkaConsumer.class);
	@KafkaListener(topics = "javaguides", groupId = "myGroup")
	public void consume(String message) {
		logger.info(String.format("message receieved by consumer: %s", message));
	}
	
	@KafkaListener(topics = "javaguides_json", groupId = "myGroup")
	public void consumeJson(User message) {
		logger.info(String.format("message receieved by consumer: %s", message));
	}
}
