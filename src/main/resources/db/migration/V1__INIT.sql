CREATE TABLE IF NOT EXISTS "orders" (
    "id"                            BIGSERIAL           PRIMARY KEY NOT NULL,
    "created_at"                    TIMESTAMP           NOT NULL,
    "updated_at"                    TIMESTAMP,
    "is_active"                     BOOLEAN             NOT NULL DEFAULT TRUE,
    "status"                        VARCHAR(50)         NOT NULL,
    "latitude_from"                 DOUBLE PRECISION    NOT NULL,
    "longitude_from"                DOUBLE PRECISION    NOT NULL,
    "latitude_to"                   DOUBLE PRECISION    NOT NULL,
    "longitude_to"                  DOUBLE PRECISION    NOT NULL,
    "description"                   VARCHAR(150)        NOT NULL
);