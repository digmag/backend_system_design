package ru.hits.core.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;

import java.util.List;
import java.util.UUID;

@FeignClient(name="user-client",url = "${USER_SERVICE_URL:http://localhost:8081}")
public interface UserClient {
    @GetMapping("/integration/information/exists/{id}")
    Boolean isUserExists(@PathVariable UUID id);

    @PostMapping("/integration/information/isadmin")
    Boolean isAdmin(@RequestBody JwtUserData userData);
}
