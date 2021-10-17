package bym.shop.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class DbTestConfig {

    @Bean
    public DataSource dataSource() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.3");
        container.start();
        return DataSourceBuilder
                .create()
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .build();
    }

}
