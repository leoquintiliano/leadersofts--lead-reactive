package br.com.apirest.leadersofts.leadcapture.adapter;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface CreateLeadAdapter {

    Mono<LeadRecord> create(LeadRecord leadRecord);

}
