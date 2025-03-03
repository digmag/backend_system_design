package ru.hits.employee.config.factory;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.hits.common.security.JwtUserData;
import ru.hits.employee.feignClient.BillClient;
import ru.hits.employee.feignClient.UserClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestFactory {
    private final UserClient userClient;
    private final BillClient billClient;

    public void create(String path, JwtUserData userData , HttpServletResponse httpResponse) throws IOException {
        if(!path.contains("/api/employee/client/login")){

            var splitPath = Arrays.stream(path.split("/")).toList();
            var exists = userClient.isUserExists(userData.getId());
            var isAdmin = userClient.isAdmin(userData);
            if(!exists || !isAdmin){
                httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
                throw new IOException();
            }

            if(splitPath.contains("users") && !splitPath.contains("bills") && splitPath.size() == 5){
                var isUserExists = userClient.isUserExists(UUID.fromString(splitPath.get(4)));
                if(!isUserExists){
                    httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                    throw new IOException();
                }
            }

            if(splitPath.contains("transactions") && splitPath.size() == 6){
                var isBillExists = billClient.isBillExists(UUID.fromString(splitPath.get(4)));
                if(!isBillExists){
                    httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                    throw new IOException();
                }
            }
        }
    }
}
