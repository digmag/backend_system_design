package ru.hits.employee.feignClient.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class FeignClientConfig {

    @Value("${app.security.integrations.api-key}")
    private String apiKey;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                requestTemplate.header("API_KEY",apiKey);
    }
}
