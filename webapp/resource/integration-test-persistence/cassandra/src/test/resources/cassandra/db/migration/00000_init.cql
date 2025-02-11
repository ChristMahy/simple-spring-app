CREATE KEYSPACE IF NOT EXISTS keyspace_placeholder
    WITH
        REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': 1 }
        and durable_writes = true;