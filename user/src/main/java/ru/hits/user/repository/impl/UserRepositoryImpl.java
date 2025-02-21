package ru.hits.user.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hits.user.repository.entity.UserEntity;
import ru.hits.user.repository.jpa.UserRepository;

import java.util.UUID;


@RequiredArgsConstructor
public class UserRepositoryImpl {
    private final UserRepository userRepository;
    public UserEntity save(UserEntity userEntity){
        userEntity.setId(UUID.randomUUID());
        return userRepository.save(userEntity);
    }
}
