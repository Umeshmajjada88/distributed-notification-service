CREATE TABLE delivery
(
    id BIGSERIAL PRIMARY KEY,

    notification_id BIGINT NOT NULL,

    event_id UUID NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    channel VARCHAR(30) NOT NULL,

    provider VARCHAR(30) NOT NULL,

    status VARCHAR(30) NOT NULL,

    attempt_count INTEGER NOT NULL,

    delivered_at TIMESTAMP,

    provider_message_id VARCHAR(255),

    failure_reason TEXT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_delivery_notification_id
ON delivery(notification_id);

CREATE INDEX idx_delivery_event_id
ON delivery(event_id);

CREATE INDEX idx_delivery_status
ON delivery(status);