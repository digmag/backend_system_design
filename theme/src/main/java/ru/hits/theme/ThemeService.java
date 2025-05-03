package ru.hits.theme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;
import ru.hits.common.security.props.SecurityIntegrationProps;
import ru.hits.common.unstable.UnstableConfig;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class})
@ConfigurationPropertiesScan("ru.hits")
@Import({GlobalExceptionHandler.class, SecurityConfig.class, SecurityIntegrationProps.class, UnstableConfig.class})
@EnableFeignClients
public class ThemeService {
    public static void main(String[] args) {
        SpringApplication.run(ThemeService.class, args);
    }
}