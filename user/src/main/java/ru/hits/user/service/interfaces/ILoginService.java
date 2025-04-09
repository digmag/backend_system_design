package ru.hits.user.service.interfaces;

import org.springframework.stereotype.Service;
import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;

import java.util.UUID;

@Service
public interface ILoginService {
    TokensPair login (String header);

    void block(UUID id);
}
