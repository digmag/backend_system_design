package ru.hits.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.hits.common.unstable.UnstableConfig;

@Configuration
@EnableAspectJAutoProxy
public class UnstableConfigg {

    @Bean
    public UnstableConfig unstableConfig(){
        return new UnstableConfig();
    }
}
