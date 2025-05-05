package ru.hits.theme.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.theme.ThemeEnum;
import ru.hits.common.dtos.theme.ThemeResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.theme.entity.ThemeEntity;
import ru.hits.theme.repository.ThemeRepository;

import java.net.Authenticator;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static ru.hits.common.security.SecurityConst.HEADER_PREFIX;

@Service
@RequiredArgsConstructor
public class ThemeServicee {
    //Сюда писать бизнес-логику
    private final ThemeRepository repository;
    public ThemeResponseDTO getTheme(Authentication authentication){
        JwtUserData data = (JwtUserData) authentication.getPrincipal();
        var theme = repository.findByUserId(data.getId()).orElse(null);
        if(theme == null) {
            return new ThemeResponseDTO(ThemeEnum.light);
        }
        return new ThemeResponseDTO(theme.getTheme());
    }

    public  ThemeResponseDTO setTheme(ThemeResponseDTO themeResponseDTO, String token, UUID ik){

        JwtUserData data = getUserId(token);
        System.out.println(data.getId());
        System.out.println(themeResponseDTO.getTheme());
        var theme = repository.findByUserId(data.getId()).orElse(null);
        ThemeEntity themeEntity = new ThemeEntity(UUID.randomUUID(), data.getId(), themeResponseDTO.getTheme());
        if(theme == null){
            repository.save(themeEntity);
            return themeResponseDTO;
        }
        theme.setTheme(themeResponseDTO.getTheme());
        repository.save(theme);
        return themeResponseDTO;
    }

    private JwtUserData getUserId(String token){
        var secret = "qqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrtttyyyuuuiiiooopppqqqwwweeerrrttty";
        JwtUserData userData;
        try{
            var key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            var data = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(HEADER_PREFIX, ""));
            var idStr = String.valueOf(data.getBody().get("id"));
            userData = new JwtUserData(
                    idStr == null ? null : UUID.fromString(idStr)
            );
            return userData;
        } catch (JwtException e) {
            return null;
        }
    }
}
