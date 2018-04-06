
psql -d aenaflight -U postgres -f aenaflight_2017_01_dump_20180327_1125.sql

psql -d aenaflight -U postgres -f migration.sql