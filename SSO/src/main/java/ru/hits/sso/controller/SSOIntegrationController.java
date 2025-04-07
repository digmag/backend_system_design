package ru.hits.sso.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.sso.service.SSOUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/integration/sso")
public class SSOIntegrationController {
    private final SSOUserService service;
    @GetMapping("/getUser")
    public UserResponseDTO getUserInfo(@RequestParam(name = "token") String token){
        return service.getUser(token);
    }
}
