#!/bin/bash

echo "Verifying DB $POSTGRES_DB presence ..."
result=`psql -v ON_ERROR_STOP=on -U "$POSTGRES_USER" -d postgres -t -c "SELECT 1 FROM pg_database WHERE datname='$POSTGRES_DB';" | xargs`
if [[ $result == "1" ]]; then
  echo "$POSTGRES_DB DB already exists"
else
  echo "$POSTGRES_DB DB does not exist, creating it ..."

  echo "Verifying role $POSTGRES_DB presence ..."
  result=`psql -v ON_ERROR_STOP=on -U "$POSTGRES_USER" -d postgres -t -c "SELECT 1 FROM pg_roles WHERE rolname='$POSTGRES_DB';" | xargs`
  if [[ $result == "1" ]]; then
    echo "$POSTGRES_DB role already exists"
  else
    echo "$POSTGRES_DB role does not exist, creating it ..."
    psql -v ON_ERROR_STOP=on -U "$POSTGRES_USER" <<-EOSQL
      CREATE ROLE $POSTGRES_DB WITH LOGIN ENCRYPTED PASSWORD '${POSTGRES_PASSWORD}';
EOSQL
    echo "$POSTGRES_DB role successfully created"
  fi

  psql -v ON_ERROR_STOP=on -U "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE $POSTGRES_DB WITH OWNER $POSTGRES_DB TEMPLATE template0 ENCODING 'UTF8';
    GRANT ALL PRIVILEGES ON DATABASE $POSTGRES_DB TO $POSTGRES_DB;
EOSQL
  result=$?
  if [[ $result == "0" ]]; then
    echo "$POSTGRES_DB DB successfully created"
  else
    echo "$POSTGRES_DB DB could not be created"
  fi
fi
echo "not end"

  psql -v ON_ERROR_STOP=on -d postgres -U "$POSTGRES_USER"  -f /home/db_startup.sql <<-EOSQL
EOSQL
  result=$?
  if [[ $result == "0" ]]; then
    echo "$POSTGRES_DB tables and schemas successfully created"
  else
    echo "$POSTGRES_DB tables and schemas could not be created"
  fi
