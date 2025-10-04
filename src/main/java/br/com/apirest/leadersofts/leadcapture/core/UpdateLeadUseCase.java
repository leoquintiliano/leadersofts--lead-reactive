package br.com.apirest.leadersofts.leadcapture.core;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

public interface UpdateLeadUseCase {
    Mono<LeadRecord> update(LeadRecord lead);
}
