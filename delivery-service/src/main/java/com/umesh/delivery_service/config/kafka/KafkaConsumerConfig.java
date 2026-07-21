package com.umesh.delivery_service.config.kafka;

import com.umesh.shared.event.NotificationDeadLetterEvent;
import com.umesh.shared.event.NotificationRequestedEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationRequestedEvent> notificationRequestedKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {

        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

        JsonDeserializer<NotificationRequestedEvent> deserializer = new JsonDeserializer<>(
                NotificationRequestedEvent.class);

        deserializer.addTrustedPackages("com.umesh.shared.event");
        deserializer.setUseTypeHeaders(false);

        ConcurrentKafkaListenerContainerFactory<String, NotificationRequestedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                new DefaultKafkaConsumerFactory<>(
                        props,
                        new StringDeserializer(),
                        deserializer));

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDeadLetterEvent> deadLetterKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {

        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

        JsonDeserializer<NotificationDeadLetterEvent> deserializer = new JsonDeserializer<>(
                NotificationDeadLetterEvent.class);

        deserializer.addTrustedPackages("com.umesh.shared.event");
        deserializer.setUseTypeHeaders(false);

        ConcurrentKafkaListenerContainerFactory<String, NotificationDeadLetterEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                new DefaultKafkaConsumerFactory<>(
                        props,
                        new StringDeserializer(),
                        deserializer));

        return factory;
    }

}