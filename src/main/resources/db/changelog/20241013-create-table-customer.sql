CREATE TABLE customer
(
    iden_customer BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name          VARCHAR(50)                             NOT NULL,
    email         VARCHAR(100),
    birthdate     date                                    NOT NULL,
    phone_number  VARCHAR(255),
    CONSTRAINT pk_customer PRIMARY KEY (iden_customer)
);