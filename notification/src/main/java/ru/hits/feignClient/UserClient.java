package ru.hits.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hits.common.dtos.user.UserResponseDTO;

import java.util.UUID;

@FeignClient(name="user-client",url = "${USER_SERVICE_URL:http://localhost:8081}")
public interface UserClient {
    @GetMapping("/integration/information/users/{id}")
    UserResponseDTO getOne(@PathVariable UUID id);
}
