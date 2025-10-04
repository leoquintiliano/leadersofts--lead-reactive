package br.com.apirest.leadersofts.leadcapture.infrastructure.factory;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;

public interface IUpdaterHandlerFactory {

    void setBasics(Lead lead, Lead leadToUpdate);

    void setCarInfo(Lead leadToUpdate, Lead lead);

    void setContactAndAndressInfo(Lead leadToUpdate, Lead lead);

}
