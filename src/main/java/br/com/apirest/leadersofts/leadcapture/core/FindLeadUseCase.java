package br.com.apirest.leadersofts.leadcapture.core;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import reactor.core.publisher.Flux;

import java.util.List;

public interface FindLeadUseCase {

    Flux<LeadRecord> findLead(String term, String type, Long id);

    Flux<LeadRecord> findLeadsById(List<Long> filteredIds);

    Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone);

    Flux<LeadRecord> findLeadsByName(String name);

    Flux<LeadRecord> findAll();

    Flux<LeadRecord> findLeadsByBirthday(String birthday);

    Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter);

}
