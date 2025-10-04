package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

public interface ICreator {
    public Mono<LeadRecord> create(LeadRecord leadDTO);
}
