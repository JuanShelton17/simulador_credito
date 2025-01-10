package com.simuladorcredito.exception;

import org.springframework.http.HttpStatus;

public class SimulacaoClienteException extends BaseException{

    public SimulacaoClienteException(HttpStatus status, String message) {
        super(status, message);
    }
}
