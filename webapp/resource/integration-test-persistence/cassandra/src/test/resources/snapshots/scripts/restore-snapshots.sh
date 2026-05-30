#!/bin/bash

KEYSPACE="$1"
CASSANDRA_DATA_DIR="/var/lib/cassandra/data/$KEYSPACE"
SNAPSHOT_NAME="test_seed"

TRUNCATE_COMMANDS=""
TABLE_DIRS=()

for dir in "${CASSANDRA_DATA_DIR}"/*; do

  table_dir=$(basename "$dir")
  table_name="${table_dir%%-*}"

  echo "Preparing truncate for $table_name"

#  TRUNCATE_COMMANDS+="TRUNCATE $KEYSPACE.$table_name;"
  TABLE_DIRS+=("$dir")

done

#echo "Executing truncates..."
#echo "$TRUNCATE_COMMANDS" | cqlsh

for dir in "${TABLE_DIRS[@]}"; do

  echo "Restoring $(basename "$dir")"
  table_name="${table_dir%%-*}"

  nodetool import -- "$KEYSPACE" "$table_name" "${dir}/snapshots/test_seed/"

done

echo "Snapshot restored from backup"