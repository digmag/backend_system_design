package ru.hits.user.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;

import java.util.List;
import java.util.UUID;

@Service
public interface IUserService {
    List<UserResponseDTO> users();
    UserResponseDTO getOne(UUID id);

    Boolean userIsAdmin(JwtUserData userData);

    Boolean isUserExists(UUID id);
}
