#!/usr/bin/env bash

KEYSPACE=taco_cloud

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.ingredients;"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.tacos;"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.orders;"