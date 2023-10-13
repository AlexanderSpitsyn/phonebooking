INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$9rGMeUQU0rjkFZpgfL6W1ue9a/dE1WUvqJ.aLEcmXFhMdSO8sR0ZG', true);

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');


INSERT INTO users (username, password, enabled)
VALUES ('user', '$2a$10$Nph6/zmxJpX6qs2y5kmsXO83izcAmCFW9PjyV/I5L1lxzu8tFCcGK', true);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');