#  docker build -f Dockerfile -t nfe/event-postgres:8 .
FROM postgres:13-alpine

RUN mkdir -p /docker-entrypoint-initdb.d
ADD db-init.sh /docker-entrypoint-initdb.d/
ADD db_startup.sql /home/db_startup.sql
RUN dos2unix /docker-entrypoint-initdb.d/db-init.sh
RUN chmod 755 /docker-entrypoint-initdb.d/db-init.sh

