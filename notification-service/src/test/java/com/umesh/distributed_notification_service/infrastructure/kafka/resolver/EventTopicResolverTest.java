// package com.umesh.distributed_notification_service.infrastructure.kafka.resolver;

// import com.umesh.distributed_notification_service.domain.outbox.constants.NotificationEventType;
// import com.umesh.shared.kafka.KafkaTopics;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// class EventTopicResolverTest {

//     private EventTopicResolver resolver;

//     @BeforeEach
//     void setUp() {
//         resolver = new EventTopicResolver();
//     }

//     @Test
//     void shouldResolveNotificationRequestedTopic() {

//         assertEquals(
//                 KafkaTopics.NOTIFICATION_REQUESTED,
//                 resolver.resolve(NotificationEventType.NOTIFICATION_REQUESTED));
//     }
// }