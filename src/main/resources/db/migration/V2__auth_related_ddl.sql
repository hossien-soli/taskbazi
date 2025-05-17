-- PostgreSQL

-- crs.password -> hashed_password - BCrypt
-- crs.verification_code -> hashed value - BCrypt
-- we should have a scheduled task to remove old garbage registration sessions
create table client_registration_sessions
(
    id UUID primary key,
    email EmailAddress not null,
    full_name UserFullName not null,
    username UserUsername not null,
    password BCryptHashResult not null,
    verification_code BCryptHashResult not null,
    request_client_id RequestClientIdentifier not null,
    attempts smallint not null,
    blocked boolean not null,
    verified boolean not null,
    registered boolean not null,
    expired boolean not null,
    created_at timestamp not null,
    closed_at timestamp null,
    version smallint null
);

create index crs_email_index on client_registration_sessions(email);
create index crs_request_client_id_index on client_registration_sessions(request_client_id);

create table users
(
    id UUID primary key,

    full_name UserFullName not null,
    email EmailAddress not null unique,
    username UserUsername not null unique,
    password BCryptHashResult not null,

    role UserRole not null,

    banned boolean not null,

    registered_at timestamp not null,

    version int null
);
-- users.full_name -> min:5
-- users.username -> min:5
-- users.password -> min:8 - hashed_password - BCrypt
-- users.banned -> user was banned by a moderator or admin!
-- users.banned -> banned users can't create new projects and invite other to a project

create type LoginSessionState as enum('ACTIVE','LOGGED_OUT','EXPIRED','INVALIDATED');

-- refresh token families
-- we should have a scheduled task for deleting old finished sessions(IMPORTANT)
create table login_sessions
(
    id UUID primary key,
    user_id UUID not null references users(id) on delete cascade on update cascade,

    refresh_count int not null,
    state LoginSessionState not null,

    request_client_id RequestClientIdentifier not null,
    request_identify_details jsonb null,

    created_at timestamp not null,
    state_updated_at timestamp null,

    version int null
);
-- refresh_token_families = login_sessions
-- each login-session(device) can logout newer login-sessions created after their own session, but not older ones
-- in-fact user session is based on a family not a simple and actual refresh-token
-- each login(with username and password) will create a new token family or login session
-- we should have a scheduled task for deleting old finished sessions(IMPORTANT)

create table refresh_tokens
(
    id UUID primary key,
    token BCryptHashResult not null,

    lifetime_hours smallint not null,
    refreshed boolean not null,

    created_at timestamp not null,
    refreshed_at timestamp null,

    session_id UUID not null references login_sessions(id) on delete cascade on update cascade,

    version smallint null
);
