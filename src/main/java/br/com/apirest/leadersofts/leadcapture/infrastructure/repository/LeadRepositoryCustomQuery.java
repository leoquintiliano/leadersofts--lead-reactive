package br.com.apirest.leadersofts.leadcapture.infrastructure.repository;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import reactor.core.publisher.Flux;

public interface LeadRepositoryCustomQuery {

    Flux<Lead> findWithFilter(LeadFilter filter);

}
