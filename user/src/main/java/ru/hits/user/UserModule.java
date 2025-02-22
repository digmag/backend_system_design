package ru.hits.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.hits")
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
public class UserModule {
    public static void main(String[] args) {
        SpringApplication.run(UserModule.class, args);
    }
}