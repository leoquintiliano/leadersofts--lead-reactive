package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.usecases;

import br.com.apirest.leadersofts.leadcapture.adapter.CreateLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.ICreator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateLeadGatewayImpl implements CreateLeadAdapter {

    private final ICreator creator;

    public CreateLeadGatewayImpl(ICreator creator) {
        this.creator = creator;
    }

    @Override
    public Mono<LeadRecord> create(LeadRecord leadDTO) {
        return creator.create(leadDTO);
    }

}
