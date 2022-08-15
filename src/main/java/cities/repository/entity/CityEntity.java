package cities.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityEntity {
    private Long id;
    private String name;
    private String photo;
    private Long total;
}
