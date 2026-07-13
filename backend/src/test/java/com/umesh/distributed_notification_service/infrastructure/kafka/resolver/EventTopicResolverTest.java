package com.umesh.distributed_notification_service.infrastructure.kafka.resolver;

import com.umesh.distributed_notification_service.common.constants.KafkaTopics;
import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTopicResolverTest {

    private EventTopicResolver resolver;

    @BeforeEach
    void setUp() {

        resolver = new EventTopicResolver();

    }

    @Test
    void shouldResolveCreatedTopic() {

        assertEquals(
                KafkaTopics.NOTIFICATION_CREATED,
                resolver.resolve(NotificationEventType.NOTIFICATION_CREATED));

    }

    @Test
    void shouldResolveRetryTopic() {

        assertEquals(
                KafkaTopics.NOTIFICATION_RETRY,
                resolver.resolve(NotificationEventType.NOTIFICATION_RETRY));

    }

    @Test
    void shouldResolveDlqTopic() {

        assertEquals(
                KafkaTopics.NOTIFICATION_DLQ,
                resolver.resolve(NotificationEventType.NOTIFICATION_FAILED));

    }

}