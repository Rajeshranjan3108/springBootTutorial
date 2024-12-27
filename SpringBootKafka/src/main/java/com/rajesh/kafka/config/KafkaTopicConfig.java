package com.rajesh.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic rajeshTopic() {
		return TopicBuilder.name("javaguides").build();
	}
	
	@Bean
	NewTopic rajeshTopicJson() {
		return TopicBuilder.name("javaguides_json").build();
	}
}
