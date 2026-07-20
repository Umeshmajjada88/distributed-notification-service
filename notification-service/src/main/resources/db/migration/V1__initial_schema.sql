CREATE TABLE notification (

    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    event_id UUID NOT NULL UNIQUE,

    channel VARCHAR(30) NOT NULL,

    status VARCHAR(30) NOT NULL,

    subject VARCHAR(255),

    message TEXT NOT NULL,

    retry_count INTEGER NOT NULL DEFAULT 0,

    scheduled_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL

);

CREATE TABLE notification_template (

    id BIGSERIAL PRIMARY KEY,

    template_name VARCHAR(100) NOT NULL UNIQUE,

    channel VARCHAR(30) NOT NULL,

    subject VARCHAR(255),

    body TEXT NOT NULL,

    created_at TIMESTAMP NOT NULL

);

CREATE TABLE notification_preference (

    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL UNIQUE,

    email_enabled BOOLEAN NOT NULL,

    sms_enabled BOOLEAN NOT NULL,

    push_enabled BOOLEAN NOT NULL

);


CREATE INDEX idx_notification_user_id
ON notification(user_id);

CREATE INDEX idx_notification_status
ON notification(status);

CREATE INDEX idx_notification_channel
ON notification(channel);

CREATE INDEX idx_notification_scheduled_at
ON notification(scheduled_at);