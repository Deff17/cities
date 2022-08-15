package unit

import cities.controller.CityController
import cities.domain.CitiesResponse
import cities.domain.City
import cities.domain.CityUpdateRequest
import cities.service.CityService
import spock.lang.Specification

class CityControllerSpec extends Specification {

    private def serviceMock = Mock(CityService)
    private def cityController = new CityController(serviceMock)

    def "Should call service method and return cities"() {
        given:
        1 * serviceMock.getCities(1, "Tokyo") >> new CitiesResponse([new City(1, "Tokyo", "http://Tokyo.jpg")], 1)
        when:
        def result = cityController.getCitiesList(1, "Tokyo")

        then:
        result == new CitiesResponse([new City(1, "Tokyo", "http://Tokyo.jpg")], 1)
    }

    def "Should call service update method"() {
        given:
        def cityUpdate = new CityUpdateRequest(1, "Tokyo", "http://Tokyo.jpg")

        when:
        cityController.updateCity(cityUpdate)

        then:
        1 * serviceMock.updateCity(cityUpdate)
    }

}
