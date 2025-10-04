package br.com.apirest.leadersofts.leadcapture.infrastructure.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
