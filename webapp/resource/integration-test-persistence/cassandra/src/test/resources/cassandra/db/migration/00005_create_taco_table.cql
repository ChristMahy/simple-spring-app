create table if not exists keyspace_placeholder.taco (
    id uuid primary key,
    createdAt timestamp,
    name text,
    ingredientIds set<uuid>
);