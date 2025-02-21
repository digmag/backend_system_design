package ru.hits.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.user.service.LoginService;
import ru.hits.user.service.interfaces.IRegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/integration/account")
public class AuthorizationController {
    private final IRegistrationService registrationService;
    private final LoginService loginService;

    @PostMapping("/registration")
    public TokensPair registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return registrationService.registration(userRegistrationDTO);
    }
}
