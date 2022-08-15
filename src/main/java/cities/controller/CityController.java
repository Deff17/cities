package cities.controller;

import cities.domain.CitiesResponse;
import cities.domain.CityUpdateRequest;
import cities.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CitiesResponse getCitiesList(@RequestParam Integer page, @RequestParam(required = false) String name) {
        return cityService.getCities(page, name);
    }

    @PutMapping("/city")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCity(@Valid @RequestBody CityUpdateRequest city) {
        cityService.updateCity(city);
    }
}
