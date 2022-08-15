package cities.service;

import cities.config.PageProperties;
import cities.domain.CitiesResponse;
import cities.domain.CityUpdateRequest;
import cities.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final PageProperties pageProperties;

    @Autowired
    public CityService(CityRepository cityRepository, PageProperties pageProperties) {
        this.cityRepository = cityRepository;
        this.pageProperties = pageProperties;
    }
    public CitiesResponse getCities(Integer page, String name) {
        return CitiesResponse.fromCitiesEntity(cityRepository.getCities(page, name, pageProperties.getPageSize()));
    }

    public void updateCity(CityUpdateRequest city) {
         cityRepository.updateCity(city.getId(), city.getName(), city.getPhoto());
    }
}
