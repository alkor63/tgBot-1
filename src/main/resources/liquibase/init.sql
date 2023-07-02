CREATE TABLE notification_task
(
    id                     BIGSERIAL    NOT NULL PRIMARY KEY,
    message                VARCHAR(255) NOT NULL,
    chat_id                BIGINT       NOT NULL,
    notification_date_time TIMESTAMP    NOT NULL
);
