package ru.hits.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserRegistrationResponse;
import ru.hits.user.service.LoginService;
import ru.hits.user.service.interfaces.ILoginService;
import ru.hits.user.service.interfaces.IRegistrationService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/integration/account")
public class AuthorizationController {
    private final IRegistrationService registrationService;
    private final ILoginService loginService;

    @PostMapping("/registration")
    public void registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        System.out.println("Начали выполнять регистрацию");
        registrationService.registration(userRegistrationDTO);
    }

    @PostMapping("/login")
    public TokensPair login(@RequestParam(name = "token") String header){
        return loginService.login(header);
    }

    @GetMapping("/block/{id}")
    public void block(@PathVariable UUID id){
        loginService.block(id);
    }
}
