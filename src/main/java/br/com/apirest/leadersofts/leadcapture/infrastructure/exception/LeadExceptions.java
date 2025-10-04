package br.com.apirest.leadersofts.leadcapture.infrastructure.exception;

import reactor.core.publisher.Mono;

public class LeadExceptions {

    public static <T> Mono<T> unreachableLeadException(String message) {
        return Mono.error(new NotFoundException(message));
    }

}
