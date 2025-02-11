create table if not exists taco_cloud.client_order (
    id uuid primary key,
    userId uuid,
    placedAt timestamp,
    deliveryName text,
    deliveryStreet text,
    deliveryCity text,
    deliveryState text,
    deliveryZip text,
    ccNumber text,
    ccExpiration text,
    ccCVV text,
    tacoIds list<uuid>
);