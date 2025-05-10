-- PostgreSQL

create type UserRole as enum('CLIENT','MODERATOR','ADMIN');
-- clients are normal users that can register in system with public registration form!

-- maybe in the future, we will move strict domain constraints here for email or username validations using CHECK
create domain RequestClientIdentifier as varchar(50);
create domain EmailAddress as varchar(60);
create domain UserFullName as varchar(40);
create domain UserUsername as varchar(30);
create domain BCryptHashResult as char(60);

create type OutboxRecordStatus as enum('CREATED','SENT','FAILED');
