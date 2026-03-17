package com.example.events.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.events.model.AdViewEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableKafka
public class KafkaConfig {

	@Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;
	
	@Bean
	public ObjectMapper objectMapper()
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
		return mapper;
	}
	
	// Producer Factory

	@SuppressWarnings({ "removal", "deprecation" })
	@Bean
	public ProducerFactory<String, Object> genericProducerFactory(ObjectMapper objectMapper){
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		config.put(ProducerConfig.ACKS_CONFIG,"all");
		config.put(ProducerConfig.RETRIES_CONFIG,3);
		config.put(ProducerConfig.LINGER_MS_CONFIG,1);
		config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"gzip");
		
		return new DefaultKafkaProducerFactory<>(
				config,
				new StringSerializer(),
				new JsonSerializer<>(objectMapper)
				);
		
	}
	
	@Bean("genericKafkaTemplate")
	public KafkaTemplate<String, Object> genericKafkaTemplate(ObjectMapper objectMapper){
		return new KafkaTemplate<>(genericProducerFactory(objectMapper));
	}
	
	// Consumer Factory Helper
	
	@SuppressWarnings("deprecation")
	private <T> ConsumerFactory<String, T> buildConsumerFactory(
			ObjectMapper objectMapper, String groupId, Class<T> targetType){
		
		JsonDeserializer<T> deserializer = new JsonDeserializer<T>(targetType);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeHeaders(false);
		Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(
            config,
            new StringDeserializer(),
            new ErrorHandlingDeserializer<>(deserializer)
        );
		
	}
	 private <T> ConcurrentKafkaListenerContainerFactory<String, T> buildContainerFactory(
	            ConsumerFactory<String, T> consumerFactory) {
	        ConcurrentKafkaListenerContainerFactory<String, T> factory =
	            new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(consumerFactory);
	        factory.setAutoStartup(true);
	        return factory;
	 }
	 
	 @Bean
	 public ConcurrentKafkaListenerContainerFactory<String, AdViewEvent> 
	 		adKafkaListenerContainerFactory(ObjectMapper objectMapper){
		return buildContainerFactory(
				buildConsumerFactory(objectMapper, "ad-group-events", AdViewEvent.class)); 
	 }

}
