package com.example.events.producer;

import org.springframework.stereotype.Service;

import com.example.events.model.AdViewEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
@Service
public class EventProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
	
	private static final String TOPIC_AD = "ad-view-events";
	
	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	public EventProducer(@Qualifier("genericKafkaTemplate") KafkaTemplate<String, Object> kafkaTemplate) {
	        this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendAdViewEvent(AdViewEvent event) {
		
		send(TOPIC_AD, event.getEventId(), event, "AdViewEvent" );
	}
	
	private void send(String topic, String key, Object event, String eventType) {
        kafkaTemplate.send(topic, key, event).whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Event [{}] sent to [{}] key={} offset={} partition={}",
                    eventType, topic, key,
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition());
            } else {
                logger.error("Failed to send event [{}] to [{}] key={} error={}",
                    eventType, topic, key, ex.getMessage());
              
            }
        });
    }
}
