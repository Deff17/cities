#!/bin/bash


CSV_LOCATION="/tmp/psql_data/cities.csv"

echo "*** FILLING DATABASE...***"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    COPY cities(id, name, photo)
    FROM '$CSV_LOCATION'
    DELIMITER ',' CSV HEADER;
EOSQL

echo "*** DATABASE FILLED! ***"

