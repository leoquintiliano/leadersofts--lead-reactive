package br.com.apirest.leadersofts.leadcapture.core;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

public interface CreateLeadUseCase {
    Mono<LeadRecord> createLead(LeadRecord lead);
}
