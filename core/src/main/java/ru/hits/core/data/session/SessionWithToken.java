package ru.hits.core.data.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionWithToken {
    private UUID userId;
    private WebSocketSession session;
}
