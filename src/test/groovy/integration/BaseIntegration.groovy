package integration

import cities.App
import groovy.transform.TypeChecked
import groovyx.net.http.RESTClient
import integration.data.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(classes = [App],
        properties = "application.environment=integration",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TypeChecked
@ActiveProfiles(["integration"])
@ContextConfiguration(classes =  [App])
class BaseIntegration extends Specification {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Value('${local.server.port}')
    protected int port;

    protected RESTClient restClient

    protected String localUrl(String endpoint) {
        return "http://localhost:$port$endpoint"
    }

    void setup() {
        restClient = new RESTClient("http://localhost:$port", "application/json")
        restClient.setHeaders(["Content-Type": MediaType.APPLICATION_JSON_VALUE, "Accept": MediaType.APPLICATION_JSON_VALUE])
        cleanDatabase()
    }

    def cleanDatabase() {
        jdbcTemplate.jdbcOperations.update("TRUNCATE cities")
    }

    def insertCitiesToDatabase(ArrayList<City> cities) {
        for(City city : cities) {
            def insertCitySql = "INSERT INTO cities (id, name, photo) VALUES (:id, :name, :photo) ON CONFLICT DO NOTHING"
            jdbcTemplate.update(insertCitySql, new BeanPropertySqlParameterSource(city))
        }
    }

}
