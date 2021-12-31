USE PM_DB;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id int NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255),
    PRIMARY KEY (id)
);
