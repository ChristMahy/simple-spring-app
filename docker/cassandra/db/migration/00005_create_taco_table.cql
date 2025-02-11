create table if not exists taco_cloud.taco (
    id uuid primary key,
    createdAt timestamp,
    name text,
    ingredientIds set<uuid>
);