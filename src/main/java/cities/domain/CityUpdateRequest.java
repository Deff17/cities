package cities.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityUpdateRequest {
    @NotNull
    private Long id;
    private String name;
    private String photo;
}
