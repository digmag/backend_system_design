package ru.hits.user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.user.repository.entity.UserEntity;
import ru.hits.user.repository.jpa.UserRepository;
import ru.hits.user.service.interfaces.ILoginService;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class LoginService implements ILoginService {
    private final UserRepository userRepository;
    private final SecurityProps securityProps;
    private final SecurityConfig securityConfig;
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

    @Override
    public TokensPair login(UserLoginDTO userLoginDTO) {
        UserEntity user = userRepository.findByEmail(userLoginDTO.getEmail()).orElse(null);
        if(user == null || !securityConfig.bCryptPasswordEncoder().matches(userLoginDTO.getPassword(), user.getPassword())){
            throw new BadRequestException("Пользователь с токим email не найден");
        }
        return new TokensPair(UUID.randomUUID().toString(),generateAccess(user));
    }
}
