package br.com.apirest.leadersofts.leadcapture.infrastructure.converter;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadDTO;

@FunctionalInterface
public interface LeadDToMapper<T> {

    LeadDTO getLeadDTO(Lead lead);

//   default void Loggin(Lead lead) {
//       System.out.println("Convertendo lead");
//    }

}
