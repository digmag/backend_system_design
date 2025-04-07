package ru.hits.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hits.common.dtos.user.UserResponseDTO;

@FeignClient(name="sso-client",url = "${SSO_CLIENT_URL:http://localhost:8086}")
public interface SSOApi {
    @GetMapping("/integration/sso/getUser")
    UserResponseDTO getUser(@RequestParam(name = "token") String token, @RequestHeader("API_KEY") String header);
}
