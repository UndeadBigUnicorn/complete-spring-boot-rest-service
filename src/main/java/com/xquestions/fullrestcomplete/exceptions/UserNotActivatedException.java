package com.xquestions.fullrestcomplete.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UserNotActivatedException extends AuthenticationException implements Supplier<UserNotActivatedException>  {

    private static final long serialVersionUID = -1126699074574529145L;

    public UserNotActivatedException(String message) {
        super(message);
    }

    @Override
    public UserNotActivatedException get() {
        return this;
    }
}
