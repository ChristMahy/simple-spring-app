create table todo(
    ID uuid default random_uuid() primary key not null,
    TITLE varchar(30) not null,
    DESCRIPTION text,
    CREATED_AT datetime
);