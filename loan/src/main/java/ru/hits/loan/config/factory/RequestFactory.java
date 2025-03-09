package ru.hits.loan.config.factory;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hits.common.security.JwtUserData;
import ru.hits.loan.feignClient.BillClient;
import ru.hits.loan.feignClient.UserClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestFactory {
    private final UserClient userClient;
    private final BillClient billClient;

    public void create(String path, JwtUserData userData , HttpServletResponse httpResponse) throws IOException {

        var splitPath = Arrays.stream(path.split("/")).toList();
        var exists = userClient.isUserExists(userData.getId());
        if (!exists) {
            httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
            throw new IOException();
        }
        if(splitPath.contains("loan") && splitPath.contains("bill") && splitPath.size() == 5) {
            if (!billClient.isBillExists(UUID.fromString(splitPath.get(4)))) {
                httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                throw new IOException();
            }
        }
    }

}
