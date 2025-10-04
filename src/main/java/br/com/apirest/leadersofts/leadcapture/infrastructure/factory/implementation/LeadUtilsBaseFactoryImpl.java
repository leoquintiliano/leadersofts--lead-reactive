package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.LeadUtilsBaseFactory;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class LeadUtilsBaseFactoryImpl implements LeadUtilsBaseFactory {

    @Override
    public void convertLeadCarInfoIntoUpperCase(Lead lead) {
        lead.setCarroAtual1((nonNull(lead.getCarroAtual1()) && !lead.getCarroAtual1().isEmpty()) ? lead.getCarroAtual1().toUpperCase() : "");
        lead.setCarroAtual2((nonNull(lead.getCarroAtual2()) && !lead.getCarroAtual2().isEmpty()) ? lead.getCarroAtual2().toUpperCase() : "");
        lead.setCarroAtual3((nonNull(lead.getCarroAtual3()) && !lead.getCarroAtual3().isEmpty()) ? lead.getCarroAtual3().toUpperCase() : "");
        lead.setCarroInteresse1((nonNull(lead.getCarroInteresse1()) && !lead.getCarroInteresse1().isEmpty()) ? lead.getCarroInteresse1().toUpperCase() : "");
        lead.setCarroInteresse2((nonNull(lead.getCarroInteresse2()) && !lead.getCarroInteresse2().isEmpty()) ? lead.getCarroInteresse2().toUpperCase() : "");
        lead.setCarroInteresse3((nonNull(lead.getCarroInteresse3()) && !lead.getCarroInteresse3().isEmpty()) ? lead.getCarroInteresse3().toUpperCase() : "");
    }

    @Override
    public void convertLeadBasicInfoIntoUpperCase(Lead lead) {
        lead.setNome((nonNull(lead.getNome()) && !lead.getNome().isEmpty()) ? lead.getNome().toUpperCase() : "");
        lead.setEmail((nonNull(lead.getEmail()) && !lead.getEmail().isEmpty()) ? lead.getEmail().toUpperCase() : "");
        lead.setEndereco((nonNull(lead.getEndereco()) && !lead.getEndereco().isEmpty()) ? lead.getEndereco().toUpperCase() : "");
    }

    @Override
    public void convertLeadExtraInformationIntoUpperCase(Lead lead) {
        lead.setVendedor((nonNull(lead.getVendedor()) && !lead.getVendedor().isEmpty()) ? lead.getVendedor().toUpperCase() : "");
        lead.setObservacoes((nonNull(lead.getObservacoes()) && !lead.getObservacoes().isEmpty()) ? lead.getObservacoes().toUpperCase() : "");
        lead.setStatus((nonNull(lead.getStatus()) && !lead.getStatus().isEmpty()) ? lead.getStatus().toUpperCase() : "");
        lead.setOpcaoVeiculo((nonNull(lead.getOpcaoVeiculo()) && !lead.getOpcaoVeiculo().isEmpty()) ? lead.getOpcaoVeiculo().toUpperCase() : "");
    }

}
