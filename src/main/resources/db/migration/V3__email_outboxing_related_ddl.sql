-- PostgreSQL

create table outbox_email_records
(
    id UUID primary key,
    email EmailAddress not null,
    subject varchar(100) not null,
    simple_message text not null,
    html_message text null,
    status OutboxRecordStatus not null,
    attempts smallint not null,
    created_at timestamp not null
);
