package ru.hits.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;
import ru.hits.common.security.props.SecurityIntegrationProps;
import ru.hits.common.security.props.SecurityProps;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.hits")
@Import({GlobalExceptionHandler.class, SecurityConfig.class, SecurityIntegrationProps.class})
public class SSOService {
    public static void main(String[] args) {
        SpringApplication.run(SSOService.class, args);
    }
}