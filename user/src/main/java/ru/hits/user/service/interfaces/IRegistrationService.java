package ru.hits.user.service.interfaces;

import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserRegistrationDTO;

@Service
public interface IRegistrationService {
    String registration(UserRegistrationDTO userRegistrationDTO);
}
