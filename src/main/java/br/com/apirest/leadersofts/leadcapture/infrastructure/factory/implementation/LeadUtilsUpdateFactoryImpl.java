package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.LeadUtilsUpdateFactory;
import org.springframework.stereotype.Component;


@Component
public class LeadUtilsUpdateFactoryImpl extends LeadUtilsBaseFactoryImpl implements LeadUtilsUpdateFactory {

    @Override
    public void transformLowerCaseInUpperCaseBeforeUpdate(Lead leadToUpdate) {
        this.convertLeadBasicInfoIntoUpperCase(leadToUpdate);
        this.convertLeadCarInfoIntoUpperCase(leadToUpdate);
        this.convertLeadExtraInformationIntoUpperCase(leadToUpdate);
    }
}
