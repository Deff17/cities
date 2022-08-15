package unit

import cities.config.PageProperties
import cities.domain.CitiesResponse
import cities.domain.City
import cities.domain.CityUpdateRequest
import cities.repository.CityRepository
import cities.repository.entity.CityEntity
import cities.service.CityService
import spock.lang.Specification

class CityServiceSpec extends Specification {
    private def repository = Mock(CityRepository)
    private def pageProperties = new PageProperties(pageSize: 5)
    private def cityService = new CityService(repository, pageProperties)

    def "should call repository with proper page size and convert cities entities to city response"() {
        when:
        def result = cityService.getCities(1, "Tokyo")
        then:
        1 * repository.getCities(1, "Tokyo", 5) >> [
                new CityEntity(1, "Tokyo", "url", 5),
                new CityEntity(2, "Jakarta", "url", 5),
                new CityEntity(3, "Delhi", "url", 5),
                new CityEntity(4, "Mumbai", "url", 5),
                new CityEntity(5, "Manila", "url", 5)
        ]
        result == new CitiesResponse(
                [new City(1, "Tokyo", "url"),
                new City(2, "Jakarta", "url"),
                new City(3, "Delhi", "url"),
                new City(4, "Mumbai", "url"),
                new City(5, "Manila", "url")],
                5L
        )

    }

    def "should call update method on repository with correct arguments"() {
        given:
        def cityRequest = new CityUpdateRequest(1, "Tokyo", "url")
        when:
        cityService.updateCity(cityRequest)
        then:
        1 * repository.updateCity(1, "Tokyo", "url")
    }
}
