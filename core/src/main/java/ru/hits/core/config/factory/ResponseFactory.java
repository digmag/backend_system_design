package ru.hits.core.config.factory;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hits.common.security.JwtUserData;
import ru.hits.core.feignClient.UserClient;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResponseFactory {

    private final UserClient userClient;

    public void create(String path, JwtUserData userData ,HttpServletResponse httpResponse) throws IOException {
        if (path.contains("/api/core/bill/create") || path.contains("/topup") || path.contains("/topdown") ||
            path.contains("/api/core/bill/my") || path.contains("/api/core/bill/transaction/")
        ) {
            if (!userClient.isUserExists(userData.getId()) || userClient.isAdmin(userData)) {
                httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
                throw new IOException();
            }
        }
        else {
            if (!userClient.isUserExists(userData.getId())){
                httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
                throw new IOException();
            }
        }


    }
}
