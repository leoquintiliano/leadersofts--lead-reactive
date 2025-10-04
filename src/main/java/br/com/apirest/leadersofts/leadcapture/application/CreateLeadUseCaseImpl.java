package br.com.apirest.leadersofts.leadcapture.application;

import br.com.apirest.leadersofts.leadcapture.adapter.CreateLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.core.CreateLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateLeadUseCaseImpl implements CreateLeadUseCase {

    private final CreateLeadAdapter createLeadAdapter;

    public CreateLeadUseCaseImpl(CreateLeadAdapter createLeadAdapter) {
        this.createLeadAdapter = createLeadAdapter;
    }

    @Override
    public Mono<LeadRecord> createLead(LeadRecord leadDTO) {
        return createLeadAdapter.create(leadDTO);
    }
}
