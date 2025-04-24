package ru.hits.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import ru.hits.common.dtos.bill.TransactionResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.core.data.session.SessionWithToken;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.hits.common.security.SecurityConst.HEADER_PREFIX;

@Component
@RequiredArgsConstructor
public class WsService implements WebSocketHandler {
    private final String secretKey = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
    private final Map<WebSocketSession, UUID> sessionWithTokenList = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var queryParams = Arrays.stream(session.getUri().getQuery().split("&")).toList();
        var token = queryParams.get(0).split("=");
        sessionWithTokenList.put(session, getIdFromToken(token[1]));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        System.out.println("Получено сообщение: " + payload);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionWithTokenList.remove(session);
        System.out.println("Отключился клиент: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Ошибка: " + exception.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessage(UUID userId1, UUID userId2, TransactionResponseDTO message) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(message);
        System.out.println("Отправка JSON: " + json);
        sessionWithTokenList.forEach((webSocketSession, uuid) -> {
            if(message.getFrom() != null){
                var userIdFrom = message.getFrom().getUserId();
                if(uuid.equals(userIdFrom)){
                    try {
                        webSocketSession.sendMessage(new TextMessage(json));
                    } catch (IOException e) {
                        System.out.println("Не удалось отправить сообщение");
                    }
                }
            }
            if(message.getTo() != null){
                var userIdTo = message.getTo().getUserId();
                if(uuid.equals(userIdTo)){
                    try{
                        webSocketSession.sendMessage(new TextMessage(json));
                    }catch (IOException e){
                        System.out.println("Не удалось отправить сообщение");
                    }
                }
            }
        });
    }

    private UUID getIdFromToken(String token) {
        try{
            var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            var data = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(HEADER_PREFIX, ""));
            var idStr = String.valueOf(data.getBody().get("id"));
            return UUID.fromString(idStr);
        } catch (JwtException e) {
            System.out.println("Токена нет");
            return null;
        }
    }
}
