package ru.hits.core.feignClient.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;

public class CustomDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        System.out.println(responseBody);
        switch (response.status()){
            case 400:
                return new BadRequestException(((RuntimeException) responseBody).getMessage());
            case 403:
                return new ForbiddenException(responseBody.toString());
            default:
                break;
        }
        return null;
    }
}
