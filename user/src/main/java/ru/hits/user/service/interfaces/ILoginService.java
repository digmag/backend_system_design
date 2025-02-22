package ru.hits.user.service.interfaces;

import ru.hits.common.dtos.token.TokensPair;
import ru.hits.common.dtos.user.UserLoginDTO;

public interface ILoginService {
    TokensPair login (UserLoginDTO userLoginDTO);
}
