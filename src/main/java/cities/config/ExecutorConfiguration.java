package cities.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfiguration {

    @Bean
    ExecutorService serviceExecutor(ExecutorProperties executorProperties) {
        return Executors.newFixedThreadPool(executorProperties.getPoolSize());
    }
}
