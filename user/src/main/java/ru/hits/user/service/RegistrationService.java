package ru.hits.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.user.service.interfaces.IRegistrationService;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {

    @Override
    public String registration(UserRegistrationDTO userRegistrationDTO) {
        return null;
    }
}
