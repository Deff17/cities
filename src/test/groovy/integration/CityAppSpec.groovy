package integration


import groovyx.net.http.HttpResponseException
import integration.data.City
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource

class CityAppSpec extends BaseIntegration {
    def cities = [
            new City(1, "Tehran", "url1"),
            new City(2, "Tianjin", "url2"),
            new City(3, "Tangshan", "url3"),
            new City(4, "Tangshan", "url4"),
            new City(5, "Taizhou", "url5"),
            new City(6, "Taiyuan", "url6")
    ]

    def setup() {
        cleanDatabase()
    }

    def "should get first 5 cities"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.get(path: "/cities", query: [
                page: 1
        ])
        then:
        result.status == 200
        result.data == [
                cities:[
                        [id:1, name:'Tehran', photo:'url1'],
                        [id:2, name:'Tianjin', photo:'url2'],
                        [id:3, name:'Tangshan', photo:'url3'],
                        [id:4, name:'Tangshan', photo:'url4'],
                        [id:5, name:'Taizhou', photo:'url5']],
                total_city_number:6]
    }

    def "should get second page of cities"() {
        given:
        def newCities = cities + [
                new City(7, "Tokyo", "url7"),
                new City(8, "Berlin", "url8"),
        ]
        insertCitiesToDatabase(newCities)
        when:
        def result = restClient.get(path: "/cities", query: [
                page: 2
        ])
        then:
        result.status == 200
        result.data == [
                cities:[
                        [id:6, name:'Taiyuan', photo:'url6'],
                        [id:7, name:'Tokyo', photo:'url7'],
                        [id:8, name:'Berlin', photo:'url8']],
                total_city_number:8]
    }

    def "should filter cities by exact name"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.get(path: "/cities", query: [
                page: 1,
                name: "Taizhou"
        ])
        then:
        result.status == 200
        result.data == [
                cities:[[id:5, name:'Taizhou', photo:'url5']],
                total_city_number:1]

    }

    def "should filter cities by prefix"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.get(path: "/cities", query: [
                page: 1,
                name: "Ta"
        ])
        then:
        result.status == 200
        result.data == [
                cities:[
                        [id:3, name:'Tangshan', photo:'url3'],
                        [id:4, name:'Tangshan', photo:'url4'],
                        [id:5, name:'Taizhou', photo:'url5'],
                        [id:6, name:'Taiyuan', photo:'url6']
                ],
                total_city_number:4]
    }

    def "should return second page filtered by name"() {
        given:
        def newCities = cities + [
                new City(7, "Tarawa", "url7"),
                new City(8, "Tangier", "url8"),
                new City(9, "Tallinn", "url9"),
        ]
        insertCitiesToDatabase(newCities)
        when:
        def result = restClient.get(path: "/cities", query: [
                page: 2,
                name: "Ta"
        ])
        then:
        result.status == 200
        result.data == [
                cities:[
                        [id:8, name:'Tangier', photo:'url8'],
                        [id:9, name:'Tallinn', photo:'url9']
                ],
                total_city_number:7]
    }


    def "should update city name"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.put(path: "/city", body: [
                id: 1,
                name: "changed"
        ])
        then:
        result.status == 204
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", 1);
        def dbRes = jdbcTemplate.query("SELECT * FROM cities WHERE id = :id", paramSource, City.cityRowMapper)
        dbRes == [new City(1, "changed", "url1")]

    }

    def "should update city photo"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.put(path: "/city", body: [
                id: 1,
                photo: "changed"
        ])
        then:
        result.status == 204
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", 1);
        def dbRes = jdbcTemplate.query("SELECT * FROM cities WHERE id = :id", paramSource, City.cityRowMapper)
        dbRes == [new City(1, "Tehran", "changed")]
    }

    def "should update city name and photo"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.put(path: "/city", body: [
                id: 1,
                name: "changed",
                photo: "changed"
        ])
        then:
        result.status == 204
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", 1);
        def dbRes = jdbcTemplate.query("SELECT * FROM cities WHERE id = :id", paramSource, City.cityRowMapper)
        dbRes == [new City(1, "changed", "changed")]
    }

    def "should NOT update request when id is missing"() {
        given:
        insertCitiesToDatabase(cities)
        when:
        def result = restClient.put(path: "/city", body: [
                name: "changed",
                photo: "changed"
        ])
        then:
        def ex = thrown(HttpResponseException)
        ex.response.status == 400

        and:

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", 1);
        def dbRes = jdbcTemplate.query("SELECT * FROM cities WHERE id = :id", paramSource, City.cityRowMapper)
        dbRes == [new City(1, "Tehran", "url1")]
    }
}
