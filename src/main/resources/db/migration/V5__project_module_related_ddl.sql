-- PostgreSQL

create type ProjectStatus as enum('REGISTERED','IN_PROGRESS','ARCHIVED','COMPLETED','CANCELLED','BLOCKED');

create table projects
(
    id UUID primary key,
    title varchar(50) not null,
    description text null,
    status ProjectStatus not null,
    registered_at timestamp not null,
    started_at timestamp null,
    closed_at timestamp null,
    edited_at timestamp null,
    version int null
);

-- Many-to-Many relation table
create table projects_users
(
    project_id UUID not null references projects(id) on delete cascade on update cascade,
    user_id UUID not null references users(id) on delete cascade on update cascade,
    owner boolean not null,
    role varchar(50) not null,
    active boolean not null,
    joined_at timestamp not null,
    version smallint null,
    primary key (project_id,user_id)
);
