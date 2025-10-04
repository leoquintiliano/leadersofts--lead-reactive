package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.find;


import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IFinderHandler {

    Flux<LeadRecord> findByType(String term, String type);

    Flux<LeadRecord> findLeadsById(List<Long> filteredIds);

    Flux<LeadRecord> findLeadsByName(String name);

    Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone);

    Flux<LeadRecord> findAll();

    Flux<LeadRecord> findLeadsByBirthday(String birthday);

    Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter);

}
