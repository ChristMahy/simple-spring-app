package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container;

public final class MySqlTestContainerConstant {

    private MySqlTestContainerConstant() {}

    public static final String KEYSPACE = "webapp_resource_cloud";

    public static final String CONTAINER_BACKUP_FILE_DIRECTORY = "/tmp/mysql_snapshot_backup/initial";
    public static final String CONTAINER_BACKUP_FILE_SQL = CONTAINER_BACKUP_FILE_DIRECTORY + "/backup.sql";

}
