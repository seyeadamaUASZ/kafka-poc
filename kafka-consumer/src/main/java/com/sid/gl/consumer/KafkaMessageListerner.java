package com.sid.gl.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListerner {
    Logger LOGGER = LoggerFactory.getLogger(KafkaMessageListerner.class);


   @KafkaListener(topics = "kafka-topic",groupId ="adama-group-1")    
    public void consume(String message){
       LOGGER.info("consumer message {}",message);
    }
    
}
