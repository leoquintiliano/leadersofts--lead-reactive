package br.com.apirest.leadersofts.leadcapture.application;

import br.com.apirest.leadersofts.leadcapture.adapter.FindLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.core.FindLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class FindLeadUseCaseImpl implements FindLeadUseCase {

    private final FindLeadAdapter adapter;

    public FindLeadUseCaseImpl(FindLeadAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Flux<LeadRecord> findLead(String term, String type, Long id) {
        return adapter.findLead(term, type);
    }

    @Override
    public Flux<LeadRecord> findLeadsById(List<Long> filteredIds) {
        return adapter.findLeadsById(filteredIds);
    }

    @Override
    public Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone) {
        return adapter.findByAnyOfTheseFilters(id,nome,celular,telefone);
    }

    @Override
    public Flux<LeadRecord> findLeadsByName(String name) {
        return adapter.findLeadsByName(name);
    }

    @Override
    public Flux<LeadRecord> findAll() {
        return adapter.findAll();
    }

    @Override
    public Flux<LeadRecord> findLeadsByBirthday(String birthday) {
        return adapter.findLeadsByBirthday(birthday);
    }

    @Override
    public Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter) {
        return adapter.findLeadsWithFilter(filter);
    }

}
