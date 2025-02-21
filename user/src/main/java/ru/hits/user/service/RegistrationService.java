package ru.hits.user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserRegistrationDTO;
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

    private final UserRepository userRepository;
    private final SecurityProps securityProps;
    @Override
    public TokensPair registration(UserRegistrationDTO userRegistrationDTO) {
        UserEntity user = new UserEntity(
                UUID.randomUUID(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPassword(),
                userRegistrationDTO.getStatus());
        userRepository.save(user);
        return new TokensPair(UUID.randomUUID().toString(), generateAccess((user)));
    }

    private String generateAccess(UserEntity user){
        var key = Keys.hmacShaKeyFor(securityProps.getJwtToken().getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(Map.of(
                        "id", user.getId()
                ))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(currentTimeMillis() + 172800000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
