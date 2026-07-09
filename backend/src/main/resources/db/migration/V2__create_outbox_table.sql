CREATE TABLE outbox_event
(
    id BIGSERIAL PRIMARY KEY,

    aggregate_type VARCHAR(100) NOT NULL,

    aggregate_id VARCHAR(100) NOT NULL,

    event_type VARCHAR(100) NOT NULL,

    topic VARCHAR(200) NOT NULL,

    payload TEXT NOT NULL,

    status VARCHAR(30) NOT NULL,

    retry_count INTEGER NOT NULL DEFAULT 0,

    published_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_outbox_status
ON outbox_event(status);

CREATE INDEX idx_outbox_event_type
ON outbox_event(event_type);

CREATE INDEX idx_outbox_aggregate_id
ON outbox_event(aggregate_id);