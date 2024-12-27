package com.rajesh.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.rajesh.kafka.model.User;

@Service
public class KafkaProducerJson {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerJson.class);

	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;

	public void produceJson(User user) {
		logger.info("Message sent is :" + user.toString());
		Message<User> message = MessageBuilder.withPayload(user).setHeader(KafkaHeaders.TOPIC, "javaguides_json").build();
		kafkaTemplate.send(message);
	}
}
