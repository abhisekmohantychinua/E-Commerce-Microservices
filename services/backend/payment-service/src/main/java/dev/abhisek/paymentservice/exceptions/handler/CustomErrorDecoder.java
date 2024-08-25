package dev.abhisek.paymentservice.exceptions.handler;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {
    Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        if (methodKey.contains("getUserByUsername")) {
            if (response.status() == 404) {
                throw new UsernameNotFoundException("Requested user name is not found on server.");
            }
        }
        return new RuntimeException();
    }
}
