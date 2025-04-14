package ru.hits.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import ru.hits.core.service.WsService;

@Configuration
@EnableWebSocket
public class WsConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WsService(),
                        "/api/ws")
                .setAllowedOriginPatterns("http://localhost:5173", "http://localhost:7000"); // 👈 CORS
    }
}
