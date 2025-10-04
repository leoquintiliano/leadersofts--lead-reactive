package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface IFinder {
    Flux<LeadRecord> findLeadsById(Collection<Long> ids);

    Flux<LeadRecord> findLeadByName(String name);

    Flux<LeadRecord> findLeadsByBirthday(String birthday);

    Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone);

    Flux<LeadRecord> findAll();

    Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter);
}
