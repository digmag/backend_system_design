package ru.hits.sso.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.sso.repository.entity.UserEntity;
import ru.hits.sso.repository.jpa.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SSOUserRegistrationService {
    private final UserRepository repository;
    private final SecurityConfig securityConfig;
    public void registration(UserRegistrationDTO userRegistrationDTO){
        if(!(repository.findByEmail(userRegistrationDTO.getEmail()).orElse(null) == null)){
            throw new BadRequestException("Пользователь с таким email уже существует");
        }
        UserEntity user = new UserEntity(
                UUID.randomUUID(),
                userRegistrationDTO.getEmail(),
                securityConfig.bCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()),
                userRegistrationDTO.getStatus(),
                true);
        repository.save(user);
    }

}
