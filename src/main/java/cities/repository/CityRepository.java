package cities.repository;

import cities.repository.entity.CityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String citiesTableName = "cities";

    private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

    @Autowired
    public CityRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CityEntity> getCities(Integer pageNumber, String name, Integer pageSize) {
        Integer offset = pageNumber * pageSize - pageSize;
        String namePhrase = name == null ? null : name + "%";
        String sql = String.format(
                "WITH records AS ( " +
                "    SELECT id, name, photo " +
                "    FROM %s " +
                "    WHERE (cast(:name as text) is null OR name like :name) " +
                "    ORDER BY id OFFSET :offset FETCH FIRST :limit ROWS ONLY " +
                "), total_count AS ( " +
                "    SELECT COUNT(*) AS total FROM %s " +
                "    WHERE (cast(:name as text) is null OR name like :name) " +
                ") " +
                "SELECT id, name, photo, total " +
                "FROM records, total_count;", citiesTableName, citiesTableName);
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name", namePhrase);
        paramSource.addValue("offset", offset);
        paramSource.addValue("limit", pageSize);
        try {
            return jdbcTemplate.query(sql, paramSource, new BeanPropertyRowMapper<>(CityEntity.class));
        } catch (DataAccessException exception) {
            log.warn("Failed to get cities", exception);
            throw exception;
        }
    }

    public void updateCity(Long id, String name, String photo) {
        String sql = String.format("UPDATE %s " +
                "SET name = COALESCE(:name, name)," +
                "photo = COALESCE(:photo, photo)" + "WHERE id = :id", citiesTableName);
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name", name);
        paramSource.addValue("photo", photo);
        paramSource.addValue("id", id);
        try {
            jdbcTemplate.update(sql, paramSource);
        } catch (DataAccessException exception) {
            log.warn(String.format("Failed to update city with id %d", id), exception);
            throw exception;
        }
    }
}
