#!/bin/sh

# Start mariadb
docker run -d --rm --name ping-database -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=test mariadb

# Start Payara Micro with link to database
docker run -d --rm -p 8080:8080 --name micro-ping --link ping-database:database payara-micro-database-ping
