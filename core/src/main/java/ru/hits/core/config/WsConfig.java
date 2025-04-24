package ru.hits.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import ru.hits.core.service.WsService;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WsConfig implements WebSocketConfigurer {
    private final WsService wsService;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsService,
                        "/api/ws")
                .setAllowedOriginPatterns("http://localhost:5173", "http://localhost:5174");
    }
}
