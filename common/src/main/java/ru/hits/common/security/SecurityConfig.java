package ru.hits.common.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.hits.common.security.props.SecurityProps;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProps.class)
public class SecurityConfig {
    private final SecurityProps securityProps;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private RequestMatcher createCustomReqMatcher(String rootPath, String... ignorePath) {
        return req -> {
            boolean result = Objects.nonNull(req.getServletPath())
                    && req.getServletPath().startsWith(rootPath)
                    && Arrays.stream(ignorePath).noneMatch(item -> req.getServletPath().startsWith(item));
            return result;
        };
    }
    @SneakyThrows
    private SecurityFilterChain finalize(HttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChainJwt(HttpSecurity http){
        http = http
                .cors(Customizer.withDefaults())
                .securityMatcher(createCustomReqMatcher(
                        securityProps.getJwtToken().getRootPath(),
                        securityProps.getJwtToken().getPermitAll()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        new JwtTokenFilter(securityProps.getJwtToken().getSecret()),
                        UsernamePasswordAuthenticationFilter.class
                );
        return finalize(http);
    }
    private RequestMatcher filterPredicate(String rootPath, String... ignore) {
        // Условие, что
        // 1. Путь сервлета задан
        // 2. Путь сервлета соответствует требуемому паттерну (начинается с rootPath)
        // 3. Путь сервлета не соответствует паттерну игнорируемых путей
        return rq -> Objects.nonNull(rq.getServletPath())
                && rq.getServletPath().startsWith(rootPath)
                && Arrays.stream(ignore).noneMatch(item ->rq.getServletPath().startsWith(item));
    }
    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChainIntegration(HttpSecurity http) {
        http = http.securityMatcher(filterPredicate(securityProps.getIntegrations().getRootPath()))
                .addFilterBefore(
                        new IntegrationAuthenticationFilter(securityProps.getIntegrations().getApiKey()),
                        UsernamePasswordAuthenticationFilter.class
                );
        return finalize(http);
    }
}
