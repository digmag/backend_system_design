package ru.hits;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;
import ru.hits.common.security.props.SecurityIntegrationProps;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class})
@ConfigurationPropertiesScan("ru.hits")
@Import({GlobalExceptionHandler.class, SecurityConfig.class, SecurityIntegrationProps.class})
@EnableFeignClients
public class NotificationServer {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServer.class, args);
    }
}
