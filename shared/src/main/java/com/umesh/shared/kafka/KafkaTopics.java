package com.umesh.shared.kafka;

public final class KafkaTopics {

    private KafkaTopics() {
    }

    public static final String NOTIFICATION_REQUESTED = "notification.requested";

    public static final String NOTIFICATION_DELIVERED = "notification.delivered";

    public static final String NOTIFICATION_FAILED = "notification.failed";

    public static final String NOTIFICATION_RETRY = "notification.retry";

}