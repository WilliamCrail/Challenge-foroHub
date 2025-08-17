package com.challenge.forohub.foro.infra.exceptions;

public class DuplicatedResourceException extends RuntimeException{
    public DuplicatedResourceException(String message) {
        super(message);
    }
}
