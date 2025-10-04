package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

public interface IUpdater {

    Mono<LeadRecord> update(Lead lead);

}
