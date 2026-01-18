package com.elotech.elobooks.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Long id) {
        super(String.format("%s not found for the given id %s", entity, id));
    }
}
