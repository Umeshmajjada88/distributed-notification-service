package com.umesh.delivery_service.config.kafka;

import com.umesh.shared.event.NotificationRequestedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, NotificationRequestedEvent> consumerFactory(
            KafkaProperties kafkaProperties) {

        Map<String, Object> config = new HashMap<>(kafkaProperties.buildConsumerProperties());

        // config.put(
        //         ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        //         StringDeserializer.class);

        // config.put(
        //         ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        //         JsonDeserializer.class);

        JsonDeserializer<NotificationRequestedEvent> deserializer = new JsonDeserializer<>(
                NotificationRequestedEvent.class);

        deserializer.addTrustedPackages("com.umesh.shared.event");

        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationRequestedEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, NotificationRequestedEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, NotificationRequestedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;

    }

}