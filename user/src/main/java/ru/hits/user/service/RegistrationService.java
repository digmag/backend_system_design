package ru.hits.user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserRegistrationResponse;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.user.repository.entity.UserEntity;
import ru.hits.user.repository.impl.UserRepositoryImpl;
import ru.hits.user.repository.jpa.UserRepository;
import ru.hits.user.service.interfaces.IRegistrationService;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {
    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;
    @Override
    public UserRegistrationResponse registration(UserRegistrationDTO userRegistrationDTO) {
        if(!(userRepository.findByEmail(userRegistrationDTO.getEmail()).orElse(null) == null)){
            throw new BadRequestException("Пользователь с таким email уже существует");
        }
        UserEntity user = new UserEntity(
                UUID.randomUUID(),
                userRegistrationDTO.getEmail(),
                securityConfig.bCryptPasswordEncoder().encode(userRegistrationDTO.getPassword()),
                userRegistrationDTO.getStatus());
        userRepository.save(user);
        return new UserRegistrationResponse(user.getId(), user.getStatus(), user.getEmail());
    }


}
