#!/usr/bin/env bash

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "CREATE KEYSPACE IF NOT EXISTS taco_cloud WITH REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': 1 } and durable_writes = true;"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "describe keyspaces;"