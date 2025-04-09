package ru.hits.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.theme.ThemeEnum;
import ru.hits.common.dtos.theme.ThemeResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.theme.entity.ThemeEntity;
import ru.hits.theme.repository.ThemeRepository;

import java.net.Authenticator;
import java.util.UUID;

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

    public  ThemeResponseDTO setTheme(Authentication authentication, ThemeResponseDTO themeResponseDTO){
        JwtUserData data = (JwtUserData) authentication.getPrincipal();
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
}
