1)Prepare database:
 - check db connection settings in db_config.properties
 - restore aenaflight_2017_01_dump_20180327_1125:
    > psql -d aenaflight -U postgres -f aenaflight_2017_01_dump_20180327_1125.sql
 - run migration script from:
    > psql -d aenaflight -U postgres -f DB_migration/migration.sql
2)Run application:
    > java -jar ETL-2.0.jar