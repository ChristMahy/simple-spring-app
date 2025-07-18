#!/usr/bin/env bash

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00000_init.cql)"

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00001_drop_all_tables.cql)"

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00002_create_role_table.cql)"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00003_create_user_tables.cql)"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00004_create_ingredient_table.cql)"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00005_create_taco_table.cql)"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00006_create_client_order_table.cql)"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "$(cat ./db/migration/00007_create_right_table.cql)"