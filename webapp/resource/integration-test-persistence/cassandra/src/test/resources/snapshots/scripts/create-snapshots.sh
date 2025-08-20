#!/bin/bash

KEYSPACE="$1"
SNAPSHOT_NAME="$2"

CASSANDRA_DATA_DIR="/var/lib/cassandra/data/$KEYSPACE"

BACKUP_DIR="/tmp/cassandra_snapshot_backup/initial"

mkdir -p "$BACKUP_DIR"

nodetool flush "$KEYSPACE"
nodetool snapshot "$KEYSPACE" -t "$SNAPSHOT_NAME"

for table_dir in "$CASSANDRA_DATA_DIR"/*; do
  if [ -d "$table_dir" ]; then
    table_name=$(basename "$table_dir")
    clean_name="${table_name%-*}"  # Strip UUID

    snapshot_path="$table_dir/snapshots/$SNAPSHOT_NAME"

    if [ -d "$snapshot_path" ]; then
      mkdir -p "$BACKUP_DIR/$clean_name"
      cp "$snapshot_path"/*.db "$BACKUP_DIR/$clean_name"/ 2>/dev/null || true
    else
      echo "No snapshot found for table: $clean_name at $snapshot_path"
    fi
  fi
done

cqlsh -e "DESC KEYSPACE $KEYSPACE" > "$BACKUP_DIR/schema.cql"

echo "Snapshot backup copied to $BACKUP_DIR"
