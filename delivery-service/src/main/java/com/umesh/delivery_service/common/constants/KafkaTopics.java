package com.umesh.delivery_service.common.constants;

public final class KafkaTopics {

    private KafkaTopics() {
    }

    public static final String NOTIFICATION_CREATED = "notification.v1.created";

    public static final String NOTIFICATION_RETRY = "notification.v1.retry";

    public static final String NOTIFICATION_DLQ = "notification.v1.dlq";

}