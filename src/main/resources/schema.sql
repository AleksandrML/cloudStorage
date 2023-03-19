CREATE TABLE users(
	username varchar PRIMARY KEY,
	password varchar);

INSERT INTO
    users (username, password)
VALUES
    ('some user','some password'),
    ('some user 2','some password 2'),
    ('some user 3','some password 3');
