package ru.hits.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.hits")
@Import({GlobalExceptionHandler.class, SecurityConfig.class})
public class LoanServer {
    public static void main(String[] args) {
        SpringApplication.run(LoanServer.class, args);
    }
}