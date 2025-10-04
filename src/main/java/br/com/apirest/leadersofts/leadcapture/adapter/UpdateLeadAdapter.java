package br.com.apirest.leadersofts.leadcapture.adapter;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

public interface UpdateLeadAdapter {

    Mono<LeadRecord> update(LeadRecord leadRecord);

}
