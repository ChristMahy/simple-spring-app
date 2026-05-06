#!/bin/bash

KEYSPACE="$1"

EXPORT_DIR="/tmp/cassandra_seed/$KEYSPACE"

mkdir -p "$EXPORT_DIR"

#echo "USE $KEYSPACE;" > "$SEED_FILE"

TABLES=$(
cqlsh -e "SELECT keyspace_name, table_name FROM system_schema.tables WHERE keyspace_name='$KEYSPACE';" \
| awk -v ks="$KEYSPACE" '$0 ~ ("^[[:space:]]*" ks "[[:space:]]*.*[a-zA-Z_]+$") { print $NF }'
)

for table in $TABLES; do

  SEED_FILE="${EXPORT_DIR}/${table}.csv"

  cqlsh -e "COPY $KEYSPACE.$table TO '$SEED_FILE' WITH HEADER=true"

done
