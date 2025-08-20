#!/bin/bash

KEYSPACE="$1"

CASSANDRA_DATA_DIR="/var/lib/cassandra/data/$KEYSPACE"

BACKUP_DIR_INITIAL="/tmp/cassandra_snapshot_backup/initial"
BACKUP_DIR="/tmp/cassandra_snapshot_backup/working_$(date +%s)"

mkdir -p "$BACKUP_DIR"

cp -r "$BACKUP_DIR_INITIAL"/* "$BACKUP_DIR"

chmod a+rwx -R "$BACKUP_DIR" 2>/dev/null

find "$CASSANDRA_DATA_DIR"/ -type f -name '*.db' -not -path '*snapshots*' -delete

cqlsh -e "DROP KEYSPACE IF EXISTS $KEYSPACE"
cqlsh -f "$BACKUP_DIR"/schema.cql

for dir in "$BACKUP_DIR"/*; do
  [ -f "$dir/schema.cql" ] && continue
  table_name=$(basename "$dir")
  echo "Import $table_name from $dir"
  nodetool import "$KEYSPACE" "$table_name" "$dir"
done

rm -rf "$BACKUP_DIR"

echo "Snapshot restored from backup"
