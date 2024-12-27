package com.rajesh;

import com.rajesh.com.rajesh.repo.WikimediaInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger logger= LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    @Autowired
    private WikimediaInterface wikimediaInterface;
    @KafkaListener(topics = "wikimedia_recentChange", groupId = "myGroup")
    public void consume(String eventMessage){
        logger.info(String.format("Message received -> %s",eventMessage));
        Wikimedia wikimedia=new Wikimedia();
        wikimedia.setWikiData(eventMessage);
        wikimediaInterface.save(wikimedia);
    }
}
