package cities.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City {
    private Long id;
    private String name;
    private String photo;
}
