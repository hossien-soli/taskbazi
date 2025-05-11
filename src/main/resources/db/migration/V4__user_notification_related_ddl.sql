-- PostgreSQL

create table persisted_notifications
(
    id UUID primary key,
    user_id UUID not null references users(id) on delete cascade on update cascade,
    subject varchar(100) not null,
    message text not null,
    html_message text null,
    important boolean not null,
    created_at timestamp not null,
    read_at timestamp null
);
