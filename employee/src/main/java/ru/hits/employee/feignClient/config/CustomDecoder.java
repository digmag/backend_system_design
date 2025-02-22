package ru.hits.employee.feignClient.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import ru.hits.common.security.exception.BadRequestException;

public class CustomDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        switch (response.status()){
            case 400:
                return new BadRequestException(responseBody.toString());
            default:
                break;
        }
        return null;
    }
}
