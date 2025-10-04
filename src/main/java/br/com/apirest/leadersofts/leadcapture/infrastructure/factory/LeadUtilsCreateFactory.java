package br.com.apirest.leadersofts.leadcapture.infrastructure.factory;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;

public interface LeadUtilsCreateFactory {

    void prepareDatesToSave(LeadRecord leadDTO, Lead lead);
    void preparePhoneBeforePersist(LeadRecord leadDTO, Lead lead);

}
