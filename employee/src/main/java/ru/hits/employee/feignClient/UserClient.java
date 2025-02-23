package ru.hits.employee.feignClient;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserRegistrationResponse;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;

import java.util.List;
import java.util.UUID;

@FeignClient(name="user-client",url = "${USER_SERVICE_URL:http://localhost:8081}")
public interface UserClient {
    @PostMapping("/integration/account/registration")
    UserRegistrationResponse registration(@RequestBody UserRegistrationDTO userRegistrationDTO);

    @PostMapping("/integration/account/login")
    TokensPair login(@RequestBody UserLoginDTO userLoginDTO);

    @GetMapping("/integration/information/users")
    List<UserResponseDTO> users();

    @GetMapping("/integration/information/users/{id}")
    UserResponseDTO getOne(@PathVariable UUID id);

    @PostMapping("/integration/information/isadmin")
    Boolean isAdmin(@RequestBody JwtUserData userData);
}
