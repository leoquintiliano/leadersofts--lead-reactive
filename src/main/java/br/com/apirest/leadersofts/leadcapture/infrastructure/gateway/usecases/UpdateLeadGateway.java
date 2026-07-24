package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.usecases;

import br.com.apirest.leadersofts.leadcapture.adapter.UpdateLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IUpdater;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation.UpdaterImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateLeadGateway implements UpdateLeadAdapter {

    private final LeadConverter converter;

    private IUpdater updater;

    public UpdateLeadGateway(LeadConverter converter, IUpdater updater) {
        this.converter = converter;
        this.updater = updater;
    }

    @Override
    public Mono<LeadRecord> update(LeadRecord leadRecord) {
        return  updater.update(converter.getLead(leadRecord));
    }

}
