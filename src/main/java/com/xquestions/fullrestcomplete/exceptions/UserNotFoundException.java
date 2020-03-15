package com.xquestions.fullrestcomplete.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException implements Supplier<UserNotFoundException> {

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public UserNotFoundException get() {
        return this;
    }
}
