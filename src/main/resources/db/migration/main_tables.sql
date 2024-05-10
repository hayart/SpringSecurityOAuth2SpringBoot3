CREATE TABLE OauthClientDetail
(
    client_id     VARCHAR(255) NOT NULL PRIMARY KEY,
    client_secret VARCHAR(255) NOT NULL,
    roles         VARCHAR(255) NOT NULL,
    name          VARCHAR(50)
);

-- OauthClientDetail values
INSERT INTO OauthClientDetail (client_id, client_secret, roles, name)
VALUES ('251e5b7daf3f29e606a84ce6e3fa361e', '1755a3c96e0787d95f3c0e0229db17c4', 'MAINTAINER', 'Maintainer');
