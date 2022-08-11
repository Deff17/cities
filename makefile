setup_postgres:
	docker build --no-cache -t postgres_with_cities .
	-docker rm -f postgres_cities
	docker run -d \
		--name postgres_cities \
		-e POSTGRES_PASSWORD=1234 \
		-e POSTGRES_DB=cities_db \
		-p 5432:5432 postgres_with_cities