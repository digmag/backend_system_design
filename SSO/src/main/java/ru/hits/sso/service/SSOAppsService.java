package ru.hits.sso.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.hits.common.security.exception.UnauthorizedException;
import ru.hits.common.security.props.SecurityProps;
import ru.hits.sso.data.Apps;
import ru.hits.sso.data.AppsCreate;
import ru.hits.sso.repository.entity.AppsEntity;
import ru.hits.sso.repository.entity.UserEntity;
import ru.hits.sso.repository.jpa.SSOAppsRepository;
import ru.hits.sso.repository.jpa.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static ru.hits.common.security.SecurityConst.HEADER_PREFIX;


@Service
@RequiredArgsConstructor
public class SSOAppsService {
    private final String secretKey = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
    private final UserRepository userRepository;
    private final SSOAppsRepository ssoAppsRepository;
    public List<Apps> getMyApps(String header){
        UserEntity user = getUser(header);
        if(user == null){
            throw new UnauthorizedException("Пользователь не найден");
        }
        return ssoAppsRepository.findAllByUserId(user.getId()).stream().map(appsEntity -> new Apps(appsEntity.getId(), appsEntity.getServiceId())).toList();
    }

    public void authowire(String authorizationHeader, AppsCreate appsCreate){
        UserEntity user = getUser(authorizationHeader);
        if(user == null){
            throw new UnauthorizedException("Пользователь не найден");
        }
        AppsEntity appsEntity = new AppsEntity(
                UUID.randomUUID(),
                appsCreate.getAppId(),
                user.getId()
        );
        ssoAppsRepository.save(appsEntity);
    }

    public UserEntity getUser(String header){
        var key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        var data = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(header.replace(HEADER_PREFIX, ""));
        String idstr = String.valueOf(data.getBody().get("id"));
        return userRepository.findById(UUID.fromString(idstr)).orElse(null);
    }
}
