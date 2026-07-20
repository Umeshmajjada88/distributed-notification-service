ALTER TABLE notification_template
RENAME COLUMN template_name TO template_code;

ALTER TABLE notification_template
ADD COLUMN name VARCHAR(150);

UPDATE notification_template
SET name = template_code
WHERE name IS NULL;

ALTER TABLE notification_template
ALTER COLUMN name SET NOT NULL;

ALTER TABLE notification_template
ADD COLUMN description VARCHAR(500);

ALTER TABLE notification_template
ADD COLUMN status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE notification_template
ADD COLUMN template_version BIGINT NOT NULL DEFAULT 1;

ALTER TABLE notification_template
ADD COLUMN is_system BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE notification_template
ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE UNIQUE INDEX uk_notification_template_code_channel
ON notification_template(template_code, channel);

CREATE INDEX idx_notification_template_status
ON notification_template(status);

CREATE INDEX idx_notification_template_channel
ON notification_template(channel);