package br.com.apirest.leadersofts.leadcapture.application;

import br.com.apirest.leadersofts.leadcapture.adapter.UpdateLeadAdapter;
import br.com.apirest.leadersofts.leadcapture.core.UpdateLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdatLeadUseCaseImpl implements UpdateLeadUseCase {

    private final UpdateLeadAdapter updateLeadAdapter;

    public UpdatLeadUseCaseImpl(UpdateLeadAdapter updateLeadAdapter) {
        this.updateLeadAdapter = updateLeadAdapter;
    }

    @Override
    public Mono<LeadRecord> update(LeadRecord lead) {
        return updateLeadAdapter.update(lead);
    }

}
