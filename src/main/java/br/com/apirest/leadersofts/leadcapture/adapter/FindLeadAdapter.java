package br.com.apirest.leadersofts.leadcapture.adapter;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import reactor.core.publisher.Flux;

import java.util.List;

public interface FindLeadAdapter {
    Flux<LeadRecord> findLead(String term, String type);

    Flux<LeadRecord> findLeadsById(List<Long> fileredIds);

    Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone);

    Flux<LeadRecord> findLeadsByName(String name);

    Flux<LeadRecord> findAll();

    Flux<LeadRecord> findLeadsByBirthday(String birthday);

    Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter);
}
