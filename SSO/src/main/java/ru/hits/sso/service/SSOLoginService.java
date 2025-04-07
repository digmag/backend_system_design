package ru.hits.sso.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserLoginDTO;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.sso.data.Token;
import ru.hits.sso.repository.entity.TokenEntity;
import ru.hits.sso.repository.entity.UserEntity;
import ru.hits.sso.repository.jpa.TokenRepository;
import ru.hits.sso.repository.jpa.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class SSOLoginService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final String secretKey = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
    private final SecurityConfig securityConfig;

    public Token login(UserLoginDTO loginDTO){
        UserEntity user = userRepository.findByEmail(loginDTO.getEmail()).orElse(null);
        if(user == null || !securityConfig.bCryptPasswordEncoder().matches(loginDTO.getPassword(), user.getPassword())){
            throw new BadRequestException("Пользователь с токим email не найден");
        }
        if(!user.isActive()){
            throw new ForbiddenException("Пользователь заблокирован");
        }
        Token token = new Token(generateToken(user));
        TokenEntity tokenEntity = new TokenEntity(
                UUID.randomUUID(),
                token.getToken(),
                user.getId()
        );
        tokenRepository.save(tokenEntity);
        return token;
    }

    private String generateToken(UserEntity user){
        var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setClaims(Map.of(
                        "id", user.getId(),
                        "sub", user.getEmail(),
                        "role", user.getStatus().toString()
                ))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(currentTimeMillis() + 10*60*1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
