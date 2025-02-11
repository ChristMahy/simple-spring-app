CREATE KEYSPACE IF NOT EXISTS taco_cloud
    WITH
        REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': 1 }
        and durable_writes = true;

describe keyspaces;