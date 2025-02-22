package ru.hits.user.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.user.Status;
import ru.hits.common.dtos.user.UserResponseDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.user.repository.jpa.UserRepository;
import ru.hits.user.service.interfaces.IUserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    @Override
    public List<UserResponseDTO> users() {
        var usersList = userRepository.findAll();
        return usersList
                .stream()
                .map(entity ->
                        new UserResponseDTO(entity.getId(), entity.getEmail(), entity.getStatus())
                )
                .toList();
    }

    @Override
    public UserResponseDTO getOne(UUID id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("Пользователь не найден");
        }
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getStatus());
    }

    @SneakyThrows
    @Override
    public Boolean userIsAdmin(JwtUserData userData) {
        var admin = userRepository.findById(userData.getId()).orElse(null);
        return admin != null || admin.getStatus() == Status.EMPLOYEE;
    }
}
