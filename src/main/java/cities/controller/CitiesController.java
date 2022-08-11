package cities.controller;

import cities.domain.Cities;
import cities.domain.CityRequest;
import cities.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class CitiesController {
    private final CitiesService citiesService;

    @Autowired
    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CompletableFuture<Cities> getCitiesList(@RequestParam Integer page, @RequestParam(required = false) String name) {
        return citiesService.getCities(page, name);
    }

    @PostMapping("/city")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> updateCity(@RequestBody CityRequest city) {
        return citiesService.updateCity(city);
    }
}
