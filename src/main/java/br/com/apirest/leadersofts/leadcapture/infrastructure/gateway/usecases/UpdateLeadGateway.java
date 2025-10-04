package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.usecases;

import br.com.apirest.leadersofts.leadcapture.adapter.UpdateLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation.UpdaterImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateLeadGateway implements UpdateLeadAdapter {

    private final LeadConverter converter;

    public UpdateLeadGateway(LeadConverter converter) {
        this.converter = converter;
    }

    @Override
    public Mono<LeadRecord> update(LeadRecord leadRecord) {
        return  UpdaterImpl.INSTANCE.update(converter.getLead(leadRecord));
    }

}
