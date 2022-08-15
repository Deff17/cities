package integration.data

import groovy.transform.Sortable
import lombok.Data
import lombok.ToString
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

@Data
@Sortable
@ToString
class City {
    Long id
    String name
    String photo

    City(Long id, String name, String photo) {
        this.id = id
        this.name = name
        this.photo = photo
    }

    public static def cityRowMapper = new RowMapper<City>() {
        @Override
        City mapRow(ResultSet rs, int rowNum) throws SQLException {
            def city = new City(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("photo"),
            )
            return city
        }
    }
}
