package br.com.apirest.leadersofts.leadcapture.infrastructure.factory;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LeadUtilsUpdateFactory {

    void transformLowerCaseInUpperCaseBeforeUpdate(Lead leadToUpdate);

}
