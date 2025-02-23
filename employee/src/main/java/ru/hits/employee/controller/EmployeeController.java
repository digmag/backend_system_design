package ru.hits.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.employee.feignClient.UserClient;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/users")
    public List<UserResponseDTO> users(Authentication authentication){
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        var isAdmin = userClient.isAdmin(userData);
        if(!isAdmin){
            throw new ForbiddenException("Пользователь не может реализовать этот запрос");
        }
        return userClient.users();
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getOne(@PathVariable UUID id, Authentication authentication){
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        var isAdmin = userClient.isAdmin(userData);
        if(!isAdmin){
            throw new ForbiddenException("Пользователь не может реализовать этот запрос");
        }
        return userClient.getOne(id);
    }
}
