package br.com.apirest.leadersofts.leadcapture.infrastructure.factory;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;

public interface LeadUtilsBaseFactory {

    void convertLeadCarInfoIntoUpperCase(Lead lead);

    void convertLeadBasicInfoIntoUpperCase(Lead lead);

    void convertLeadExtraInformationIntoUpperCase(Lead lead);

}
