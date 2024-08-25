package dev.abhisek.orderservice.exceptions.handler;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        System.out.println(methodKey);
        System.out.println(response);
        return null;
    }
}
