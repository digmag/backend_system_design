package ru.hits.employee;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;
import ru.hits.common.security.props.SecurityIntegrationProps;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.hits")
@Import({GlobalExceptionHandler.class, SecurityConfig.class, SecurityIntegrationProps.class})
@EnableFeignClients
public class EmployeeService {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeService.class, args);
    }
}