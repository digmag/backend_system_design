package ru.hits.core;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.hits")
@EnableFeignClients
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
public class Core {
    public static void main(String[] args) {
        SpringApplication.run(Core.class, args);
    }
}