package cities.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;

@Configuration
public class PostgresConfiguration {

    @Bean
    NamedParameterJdbcTemplate getJdbcTemplate(PostgresProperties postgresProperties) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(postgresProperties.getDriverClass());
        dataSource.setUrl(postgresProperties.getUrl());
        dataSource.setUsername(postgresProperties.getUser());
        dataSource.setPassword(postgresProperties.getPassword());
        dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
