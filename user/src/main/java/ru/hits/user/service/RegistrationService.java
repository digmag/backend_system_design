package ru.hits.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.exception.UnauthorizedException;
import ru.hits.user.client.SSOApi;
import ru.hits.user.service.interfaces.IRegistrationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {
    private final SSOApi ssoApi;
    @Override
    public void registration(UserRegistrationDTO userRegistrationDTO) {
        try{
            ssoApi.registration(userRegistrationDTO);
        }
        catch (RuntimeException e){
            System.out.println("Пользователь с таким email существует");
        }
    }
}
