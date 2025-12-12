CREATE TABLE IF NOT EXISTS "merchants" (
    "id"                            BIGSERIAL           PRIMARY KEY NOT NULL,
    "created_at"                    TIMESTAMP           NOT NULL,
    "updated_at"                    TIMESTAMP,
    "is_active"                     BOOLEAN             NOT NULL DEFAULT TRUE,
    "name"                          VARCHAR(150)        NOT NULL
);