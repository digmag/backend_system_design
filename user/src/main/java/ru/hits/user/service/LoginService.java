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
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.user.repository.entity.TokenEntity;
import ru.hits.user.repository.entity.UserEntity;
import ru.hits.user.repository.jpa.TokenRepository;
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
    private final TokenRepository tokenRepository;
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
        if(!user.isActive()){
            throw new ForbiddenException("Пользователь заблокирован");
        }
        TokensPair tokensPair = new TokensPair(UUID.randomUUID().toString(),generateAccess(user));
        TokenEntity token = new TokenEntity(
                UUID.randomUUID(),
                tokensPair.getAccessToken(),
                tokensPair.getRefreshToken(),
                user.getId()
                );
        tokenRepository.save(token);
        return tokensPair;
    }

    @Override
    public void block(UUID id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("Пользователь не найден");
        }
        user.setActive(false);
        userRepository.save(user);
    }
}
