package ru.hits.employee.feignClient;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserRegistrationDTO;

@FeignClient(name="user-client",url = "http://localhost:8081")
public interface UserClient {
    @PostMapping("/integration/account/registration")
    TokensPair registration(@RequestBody UserRegistrationDTO userRegistrationDTO);
}
