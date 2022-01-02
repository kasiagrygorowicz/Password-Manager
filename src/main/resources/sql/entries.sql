USE PM_DB;
DROP TABLE IF EXISTS entries;

CREATE TABLE entries (
    id int NOT NULL AUTO_INCREMENT,
    link varchar(255) NOT NULL,
    password varchar(60) not null,
	user_id int not null,
    PRIMARY KEY (id),
    foreign key(user_id) REFERENCES users(id)
);