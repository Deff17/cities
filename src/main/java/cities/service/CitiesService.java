package cities.service;

import cities.config.PageProperties;
import cities.domain.Cities;
import cities.domain.CityRequest;
import cities.repository.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class CitiesService {
    private final CitiesRepository citiesRepository;
    private final Executor executor;
    private final PageProperties pageProperties;

    @Autowired
    public CitiesService(CitiesRepository citiesRepository, Executor executor, PageProperties pageProperties) {
        this.citiesRepository = citiesRepository;
        this.executor = executor;
        this.pageProperties = pageProperties;
    }
    public CompletableFuture<Cities> getCities(Integer page, String name) {
        return CompletableFuture.supplyAsync(() -> citiesRepository.getCities(page, name, pageProperties.getPageSize()))
                       .thenApply(Cities::fromCitiesDto);
    }

    public CompletableFuture<Void> updateCity(CityRequest city) {
        return CompletableFuture.runAsync(() -> citiesRepository.updateCity(city.getId(), city.getName(), city.getPhoto()), executor);
    }
}
