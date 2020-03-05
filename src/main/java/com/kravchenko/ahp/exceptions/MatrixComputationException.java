package com.kravchenko.ahp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MatrixComputationException extends RuntimeException implements Supplier<MatrixComputationException> {

    public MatrixComputationException(String message) {
        super(message);
    }

    @Override
    public MatrixComputationException get() {
        return this;
    }

}
