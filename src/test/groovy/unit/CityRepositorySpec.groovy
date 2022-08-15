package unit

import cities.repository.CityRepository
import cities.repository.entity.CityEntity
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import spock.lang.Specification

import java.sql.SQLException

class CityRepositorySpec extends Specification {
    def jdbcTemplate = Mock(NamedParameterJdbcTemplate)
    def cityRepository = new CityRepository(jdbcTemplate)

    def "should call query method once when getting cities"() {
        when:
        def result = cityRepository.getCities(1, "Tokyo", 5)
        then:
        1 * jdbcTemplate.query(_, _, _) >> [
                new CityEntity(1, "Tokyo", "url", 2),
                new CityEntity(2, "Jakarta", "url", 2),
        ]

        result == [
                new CityEntity(1, "Tokyo", "url", 2),
                new CityEntity(2, "Jakarta", "url", 2),
        ]
    }

    def "should throw exception when query failed"() {
        given:
        1 * jdbcTemplate.query(_, _, _) >> {throw new SQLException()}
        when:
        cityRepository.getCities(1, "Tokyo", 5)
        then:
        thrown(SQLException)
    }

    def "should call query method once when updating city"() {
        when:
        cityRepository.updateCity(1, "Tok", "url")
        then:
        1 * jdbcTemplate.update(_, _)
    }

    def "should throw exception when update failed"() {
        given:
        1 * jdbcTemplate.update(_, _) >> {throw new SQLException()}
        when:
        cityRepository.updateCity(1, "Tok", "url")
        then:
        thrown(SQLException)
    }
}
