#!/bin/bash

KEYSPACE="$1"
SNAPSHOT_NAME="test_seed"

nodetool cleanup "$KEYSPACE"
nodetool snapshot -t "$SNAPSHOT_NAME" -- "$KEYSPACE"