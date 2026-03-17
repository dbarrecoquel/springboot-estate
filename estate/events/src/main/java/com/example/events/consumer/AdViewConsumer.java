package com.example.events.consumer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.events.model.AdViewEvent;

@Service
public class AdViewConsumer {
	 private static final Logger logger = LoggerFactory.getLogger(AdViewConsumer.class);
	    
	@PostConstruct
    public void init() {
        logger.info("AdViewConsumer initialized and ready to consume events");
    }
    
    @KafkaListener(
        topics = "ad-view-events",
        groupId = "ad-events-group",
        containerFactory = "adKafkaListenerContainerFactory" 
    )
    public void consume(AdViewEvent event) {
        logger.info("Received AdViewEvent: {}", event);
        
        try {
            processEvent(event);
            logger.info("AdViewEvent processed successfully: {}", event.getEventId());
        } catch (Exception e) {
            logger.error("Error processing AdViewEvent: {}", event.getEventId(), e);
        }
    }
    
    private void processEvent(AdViewEvent event) {
        
    }
}
