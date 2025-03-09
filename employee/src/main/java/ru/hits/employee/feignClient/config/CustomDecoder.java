package ru.hits.employee.feignClient.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;

public class CustomDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        switch (response.status()){
            case 400:
                throw new BadRequestException("Ошибка 404");
            case 403:
                throw new ForbiddenException("Ошибка 403");
            default:
                break;
        }
        return null;
    }
}
