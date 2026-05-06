#!/bin/bash

KEYSPACE="$1"

IMPORT_DIR="/tmp/cassandra_seed/$KEYSPACE"

find "$IMPORT_DIR" -type f -name "*.csv" -print0 | \
while IFS= read -r -d '' file; do

  filename=$(basename "$file")
  table="${filename%.csv}"

  echo "TRUNCATE $KEYSPACE.$table;"

  if [ "$(wc -l < "$file")" -gt 1 ]; then

    echo "COPY $KEYSPACE.$table FROM '$file' WITH HEADER=true;"

  fi

done | \
cqlsh