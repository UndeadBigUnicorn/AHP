package com.kravchenko.ahp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MatrixComputationException extends ResponseStatusException implements Supplier<MatrixComputationException> {

    public MatrixComputationException(String message) {
        super(HttpStatus.BAD_REQUEST, message, null);
    }

    @Override
    public MatrixComputationException get() {
        return this;
    }

}
