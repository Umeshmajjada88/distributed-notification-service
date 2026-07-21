CREATE TABLE dead_letter (

    id BIGSERIAL PRIMARY KEY,

    delivery_id BIGINT NOT NULL,

    notification_id BIGINT NOT NULL,

    event_id UUID NOT NULL,

    channel VARCHAR(20) NOT NULL,

    provider VARCHAR(50) NOT NULL,

    attempt_count INTEGER NOT NULL,

    failure_reason VARCHAR(500) NOT NULL,

    payload TEXT,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL

);