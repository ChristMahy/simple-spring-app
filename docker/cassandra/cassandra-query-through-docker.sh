#!/usr/bin/env bash

KEYSPACE=taco_cloud

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.role;"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.user;"

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.ingredient;"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.taco;"
docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.client_order;"

docker exec -it cassandra cqlsh -u cassandra -p cassandra -e "select * from $KEYSPACE.user where username = 'machine2machine' and authprovider = 'LOCAL' and discriminator = '1' allow filtering;"