package com.rajesh.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.kafka.model.User;
import com.rajesh.kafka.producer.KafkaProducer;
import com.rajesh.kafka.producer.KafkaProducerJson;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private KafkaProducerJson kafkaProducerJson;
	
	@GetMapping("/publish")
	public ResponseEntity<String> publish(@RequestParam String message){
		kafkaProducer.sendMessage(message);
		return new ResponseEntity("Message is sent",HttpStatus.OK);
	}
	
	@PostMapping("/publishJson")
	public ResponseEntity<String> publishJson(@RequestBody User user){
		kafkaProducerJson.produceJson(user);
		return new ResponseEntity("Json Message is sent",HttpStatus.OK);
	}
}
