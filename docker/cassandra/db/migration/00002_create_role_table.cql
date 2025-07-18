create table if not exists taco_cloud.role (
    id uuid primary key,
    name text,
    cassandraRightIds list<uuid>
);