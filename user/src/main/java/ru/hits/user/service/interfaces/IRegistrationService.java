package ru.hits.user.service.interfaces;

import org.springframework.stereotype.Service;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserRegistrationDTO;
import ru.hits.common.dtos.user.UserRegistrationResponse;

@Service
public interface IRegistrationService {
    void registration(UserRegistrationDTO userRegistrationDTO);
}
