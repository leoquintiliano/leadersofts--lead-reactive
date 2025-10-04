package br.com.apirest.leadersofts.leadcapture.infrastructure.factory;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

public interface LeadUtilsFindFactory {
    Mono<LeadRecord> findLead(String term, String type);
}
