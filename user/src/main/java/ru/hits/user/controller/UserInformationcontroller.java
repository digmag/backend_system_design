package ru.hits.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.user.repository.jpa.UserRepository;
import ru.hits.user.service.interfaces.IUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/integration/information")
public class UserInformationcontroller {
    private final IUserService userService;

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers(){
        return userService.users();
    }

    @GetMapping("/users/{id}")
    public UserResponseDTO getUser(@PathVariable UUID id){
        return userService.getOne(id);
    }

    @PostMapping("/isadmin")
    public Boolean isAdmin(@RequestBody JwtUserData userData){
        return userService.userIsAdmin(userData);
    }

    @GetMapping("/isexists/{id}")
    public Boolean isUserExists(@PathVariable UUID id){
        return userService.isUserExists(id);
    }
}
