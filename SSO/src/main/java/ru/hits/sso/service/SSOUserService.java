package ru.hits.sso.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.exception.UnauthorizedException;
import ru.hits.sso.repository.entity.UserEntity;
import ru.hits.sso.repository.jpa.UserRepository;

@Service
@RequiredArgsConstructor
public class SSOUserService {
    private final SSOAppsService ssoAppsService;

    public UserResponseDTO getUser(String header){
        UserEntity user = ssoAppsService.getUser(header);
        if(user == null){
            throw new UnauthorizedException("Пользователь не найден");
        }
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getStatus(),
                user.isActive()
        );
    }
}
