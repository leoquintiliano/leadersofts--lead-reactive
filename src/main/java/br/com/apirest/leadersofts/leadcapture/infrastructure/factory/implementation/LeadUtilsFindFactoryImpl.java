package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.LeadUtilsFindFactory;
import reactor.core.publisher.Mono;

public class LeadUtilsFindFactoryImpl implements LeadUtilsFindFactory {

    @Override
    public Mono<LeadRecord> findLead(String term, String type) {
        return null;
    }
}
