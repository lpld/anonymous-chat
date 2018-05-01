# --- !Ups

CREATE TABLE chat_user (

  id            BIGSERIAL PRIMARY KEY,
  login         VARCHAR NOT NULL UNIQUE,
  password_hash VARCHAR,
  name          VARCHAR
);

# --- !Downs
DROP TABLE chat_user;
