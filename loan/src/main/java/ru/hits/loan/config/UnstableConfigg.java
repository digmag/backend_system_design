package ru.hits.loan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hits.common.unstable.UnstableConfig;

@Configuration
public class UnstableConfigg {

    @Bean
    public UnstableConfig unstableConfig(){
        return new UnstableConfig();
    }
}
