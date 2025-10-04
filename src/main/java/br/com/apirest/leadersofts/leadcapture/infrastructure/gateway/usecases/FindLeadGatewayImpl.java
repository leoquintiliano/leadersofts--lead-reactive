package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.usecases;

import br.com.apirest.leadersofts.leadcapture.adapter.FindLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.datasource.FinderHandlerDataSource;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IFinder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class FindLeadGatewayImpl implements FindLeadAdapter {

    private IFinder finder;

//    public FindLeadGatewayImpl(@Qualifier("FinderImpl") IFinder finder) {
//        this.finder = finder;
//    }

    public FindLeadGatewayImpl(IFinder finder) {
        this.finder = finder;
    }

    @Override
    public Flux<LeadRecord> findLead(String term, String type) {
        return FinderHandlerDataSource.getInstance().findByType(term,type);
    }

    @Override
    public Flux<LeadRecord> findLeadsById(List<Long> fileredIds) {
        return finder.findLeadsById(fileredIds);
    }

    @Override
    public Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone) {
        return finder.findByAnyOfTheseFilters(id,nome,celular,telefone);
    }

    @Override
    public Flux<LeadRecord> findLeadsByName(String name) {
        return finder.findLeadByName(name);
    }

    @Override
    public Flux<LeadRecord> findAll() {
        return finder.findAll();
    }

    @Override
    public Flux<LeadRecord> findLeadsByBirthday(String birthday) {
        return finder.findLeadsByBirthday(birthday);
    }

    @Override
    public Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter) {
        return finder.findLeadsWithFilter(filter);
    }

}
