package ru.hits.core.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import ru.hits.common.security.JwtUserData;
import ru.hits.core.data.session.SessionWithToken;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.hits.common.security.SecurityConst.HEADER_PREFIX;

@Component
@RequiredArgsConstructor
public class WsService implements WebSocketHandler {
    private final String secretKey = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
    private final List<SessionWithToken> sessionWithTokenList = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var queryParams = Arrays.stream(session.getUri().getQuery().split("&")).toList();
        var token = queryParams.get(0).split("=");
        sessionWithTokenList.add(new SessionWithToken(
                getIdFromToken(token[1]),
                session
        ));
        System.out.println("Подключился клиент: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        System.out.println("Получено сообщение: " + payload);

        for (var s : sessionWithTokenList) {
            s.getSession().sendMessage(new TextMessage("Hello, " + payload));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        var ref = new Object() {
            WebSocketSession wsSession;
        };
        sessionWithTokenList.forEach(sessionWithToken -> {
            if(Objects.equals(session, sessionWithToken)){
                ref.wsSession = sessionWithToken.getSession();
            }
        });
        sessionWithTokenList.remove(ref.wsSession);
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

    public void sendMessage(UUID userId1, UUID userId2, WebSocketMessage<?> message) {
        System.out.println(sessionWithTokenList);
        sessionWithTokenList.forEach(sessionWithToken -> {
           try {
               sessionWithToken.getSession().sendMessage(message);
           }
           catch (Exception e) {
               System.out.println("Не удалось отправить сообщение");
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
