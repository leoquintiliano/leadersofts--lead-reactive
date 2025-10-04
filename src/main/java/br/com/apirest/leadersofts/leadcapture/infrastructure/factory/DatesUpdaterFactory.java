package br.com.apirest.leadersofts.leadcapture.infrastructure.factory;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;

import java.util.List;

public interface DatesUpdaterFactory {

    void formatDateFields(Lead leadToUpdate, Lead lead);

    void prepareDatesToUpdte(Lead leadToUpdate, Lead lead);

    void updateDatesIfNecessary(Lead leadToUpdate, List<Long> calculatedDates);

}
