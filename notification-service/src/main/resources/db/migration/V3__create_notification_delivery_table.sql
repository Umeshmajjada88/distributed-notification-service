CREATE TABLE notification_delivery (

    id BIGSERIAL PRIMARY KEY,

    notification_id BIGINT NOT NULL,

    channel VARCHAR(30) NOT NULL,

    attempt_number INTEGER NOT NULL,

    status VARCHAR(30) NOT NULL,

    error_message TEXT,

    started_at TIMESTAMP,

    completed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_notification_delivery_notification
        FOREIGN KEY (notification_id)
        REFERENCES notification(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_delivery_notification
ON notification_delivery(notification_id);

CREATE INDEX idx_delivery_status
ON notification_delivery(status);

CREATE INDEX idx_delivery_channel
ON notification_delivery(channel);