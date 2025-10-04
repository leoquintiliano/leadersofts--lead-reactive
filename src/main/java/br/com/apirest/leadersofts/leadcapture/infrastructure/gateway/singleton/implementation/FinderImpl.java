package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.find.IFinderHandler;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IFinder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service("FinderImpl")
public class FinderImpl implements IFinder {

    private static FinderImpl INSTANCE;

    private static IFinderHandler handler;

    public FinderImpl(IFinderHandler handler) {
        this.handler = handler;
    }

    public static synchronized FinderImpl getInstance() {
        return Objects.isNull(INSTANCE) ? new FinderImpl(handler) : INSTANCE;
    }

    @Override
    public Flux<LeadRecord> findLeadsById(Collection<Long> ids) {
        return handler.findLeadsById((List<Long>) ids);
    }

    @Override
    public Flux<LeadRecord> findLeadByName(String name) {
        return handler.findLeadsByName(name);
    }

    @Override
    public Flux<LeadRecord> findLeadsByBirthday(String birthday) {
        return handler.findLeadsByBirthday(birthday);
    }

    @Override
    public Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone) {
        return handler.findByAnyOfTheseFilters(id, nome, celular, telefone);
    }

    @Override
    public Flux<LeadRecord> findAll() {
        return handler.findAll();
    }

    @Override
    public Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter) {
        return handler.findLeadsWithFilter(filter);
    }

}
