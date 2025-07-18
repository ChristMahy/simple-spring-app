create table if not exists keyspace_placeholder.role (
    id uuid primary key,
    name text,
    cassandraRightIds list<uuid>
);