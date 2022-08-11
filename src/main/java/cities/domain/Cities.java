package cities.domain;

import cities.repository.dto.CityDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class Cities {
    @JsonProperty("cities")
    private List<City> cities;
    @JsonProperty("total_city_number")
    private Long totalCityNumber;

    public Cities(List<City> cities, Long totalCityNumber) {
        this.cities = cities;
        this.totalCityNumber = totalCityNumber;
    }

    public static Cities fromCitiesDto(List<CityDto> cityDtos) {
        return new Cities(
                cityDtos.stream().map(cityDto -> new City(cityDto.getId(), cityDto.getName(), cityDto.getPhoto())).collect(Collectors.toList()),
                cityDtos.stream().findFirst().map(CityDto::getTotal).orElse(0L));
    }
}