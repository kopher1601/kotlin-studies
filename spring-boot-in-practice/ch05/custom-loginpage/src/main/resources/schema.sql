CREATE TABLE courses
(
    id          BIGINT  NOT NULL auto_increment,
    category    VARCHAR(255),
    description VARCHAR(255),
    name        VARCHAR(255),
    rating      INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);

CREATE TABLE authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index ix_auth_username on authorities (username, authority);