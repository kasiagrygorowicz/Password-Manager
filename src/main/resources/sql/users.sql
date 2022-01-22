USE PM_DB;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id int NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    master_password varchar(255) NOT NULL,
    iv varbinary(255),
    salt varbinary(255),
    is_active boolean,
    lock_time datetime,
    login_attempts int NOT NULL,

    PRIMARY KEY (id)
);
