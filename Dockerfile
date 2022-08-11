FROM postgres:13.3

RUN mkdir -p /tmp/psql_data/

COPY src/main/resources/postgres/cities.csv /tmp/psql_data/
COPY src/main/resources/postgres/create.sql /docker-entrypoint-initdb.d/create_tables.sql
COPY src/main/resources/postgres/init_postgres.sh /docker-entrypoint-initdb.d/