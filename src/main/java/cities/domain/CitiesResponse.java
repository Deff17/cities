package cities.domain;

import cities.repository.entity.CityEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class CitiesResponse {
    private List<City> cities;
    @JsonProperty("total_city_number")
    private Long totalCityNumber;

    public static CitiesResponse fromCitiesEntity(List<CityEntity> cityEntities) {
        return new CitiesResponse(
                cityEntities.stream().map(cityEntity -> new City(cityEntity.getId(), cityEntity.getName(), cityEntity.getPhoto())).collect(Collectors.toList()),
                cityEntities.stream().findFirst().map(CityEntity::getTotal).orElse(0L));
    }
}