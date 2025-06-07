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
    managing_assign boolean not null,
    self_assign boolean not null,
    active boolean not null,
    joined_at timestamp not null,
    primary key (project_id,user_id)
);

create type TaskPriority as enum('CRITICAL','HIGH','MEDIUM','LOW','OPTIONAL');
create type TaskStatus as enum('ASSIGNED','ACCEPTED','IN_PROGRESS','COMPLETED','VERIFIED','CANCELLED','REJECTED');

-- assigned_by(user_id) can be null but it is not optional and on task creation it should set
-- assigned_to(user_id) can be null but it is not optional and on task creation it should set
-- we should not delete task if user has been deleted!!! they should keep in the project history
create table tasks
(
    id UUID primary key,
    project_id UUID not null references projects(id) on delete cascade on update cascade,
    assigned_by UUID null references users(id) on delete set null on update cascade,
    assigned_to UUID null references users(id) on delete set null on update cascade,
    title varchar(45) not null,
    description text null,
    priority TaskPriority not null,
    status TaskStatus not null,
    due timestamp null,
    reject_reason text null,
    cancel_reason text null,
    assigned_at timestamp not null,
    accepted_at timestamp null,
    started_at timestamp null,
    completed_at timestamp null,
    cancelled_at timestamp null,
    rejected_at timestamp null,
    verified_at timestamp null,
    version int null
);
