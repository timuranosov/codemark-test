create schema codemark;
create user 'codemark'@'localhost' identified by 'codemark';
grant all privileges on codemark.* to 'codemark'@'localhost';
grant all privileges on codemark_test.* to 'codemark'@'localhost';
create schema codemark_test;

-- Production tables
CREATE TABLE codemark.user
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT user_login UNIQUE (login)
);

CREATE TABLE codemark.role
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE codemark.user_roles
(
    user_id BIGINT(20) NOT NULL,
    role_id BIGINT(20) NOT NULL
);

-- Tables for unit-test schema
CREATE TABLE codemark_test.user
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT user_login UNIQUE (login)
);

CREATE TABLE codemark_test.role
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE codemark_test.user_roles
(
    user_id BIGINT(20) NOT NULL,
    role_id BIGINT(20) NOT NULL
);