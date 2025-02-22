package ru.hits.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.employee.feignClient.UserClient;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/employee")
public class EmployeeController {
    @Autowired
    private final UserClient userClient;
    @PostMapping("/client/registration")
    public String registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        return userClient.registration(userRegistrationDTO);
    }

    @PostMapping("/client/login")
    public TokensPair login(@RequestBody UserLoginDTO userLoginDTO){
        return userClient.login(userLoginDTO);
    }
}
