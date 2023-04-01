package ru.job4j.shortcut.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceDoesNotExistException extends RuntimeException {

    public ResourceDoesNotExistException(String message) {
        super(message);
    }
}
