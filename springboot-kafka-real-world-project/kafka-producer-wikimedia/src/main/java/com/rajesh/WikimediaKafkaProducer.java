package com.rajesh;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaKafkaProducer {

	private static final Logger logger=LoggerFactory.getLogger(WikimediaKafkaProducer.class);
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage() throws InterruptedException {
		String topic="wikimedia_recentChange";
		
		//to read real time stream data from wikimedia
		EventHandler eventHandler =new WikimediaChangesHandler(topic,kafkaTemplate);
		String url="https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource.Builder buidler=new EventSource.Builder(eventHandler, URI.create(url));
		EventSource eventSource=buidler.build();
		eventSource.start();
		TimeUnit.MINUTES.sleep(10);
	}
}
