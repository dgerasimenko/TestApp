1)Prepare database:
 - check db connection settings in app_config.properties
 - restore aenaflight_2017_01_dump_20180327_1125:
    > psql -d aenaflight -U postgres -f aenaflight_2017_01_dump_20180327_1125.sql
 - run migration script from:
    > psql -d aenaflight -U postgres -f DB_migration/migration.sql
2)Run application:
    > java -Xmx2g -jar ETL-2.1.jar
	
Note: You can modify pool settings and max amount of records (chunk.size) being processed by single step/task 
      Please be careful this would cause OutOfMemoryError.