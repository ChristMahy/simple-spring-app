create table if not exists keyspace_placeholder.user (
    id uuid primary key,
    userName text,
    password blob,
    fullName text,
    street text,
    city text,
    state text,
    zip text,
    phoneNumber text,
    cassandraRoleIds set<uuid>,
    discriminator text,
    authProvider text,
    isExpired boolean,
    isLocked boolean,
    isEnabled boolean,
    isCredentialsExpired boolean
);