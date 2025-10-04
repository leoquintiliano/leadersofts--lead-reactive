//package br.com.apirest.leadersofts.leadcapture.infrastructure.config;
//
//
//import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
//import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
//import org.modelmapper.ModelMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ModelMapperConfig {
//
//    @Bean
//    public ModelMapper modelMapper() {
//        var modelMapper = new ModelMapper();
//
//        modelMapper.createTypeMap(Lead.class, LeadRecord.class);
////        modelMapper.map(Lead.class, LeadRecord.class);
////                .addMapping(src -> src.getNome(), (dest, value) -> dest.nome("") ) )
//        return modelMapper;
//    }
//
//
//
//    public LeadRecord getLeadRecord(Lead lead) {
//        return new LeadRecord(lead.getId(), lead.getNome(), lead.getPrimeiroContato(), lead.getUltimoContato(), lead.getDataNascimento(),
//                lead.getCelular(), lead.getCelular2(), lead.getTelefone(), lead.getEndereco(), lead.getEmail(), lead.getUf(), lead.getCidade(),
//                lead.getCarroInteresse1(), lead.getCarroInteresse2(),lead.getCarroInteresse3(), lead.getCarroAtual1(), lead.getCarroAtual2(),
//                lead.getCarroAtual3(), lead.getVendedor(), lead.getStatus(), lead.getOpcaoVeiculo(), lead.getObservacoes(),
//                lead.getDataCadastro(), lead.getDiasCadastro(), lead.getDiasUltimoContato(), lead.getDataVenda(), lead.getDiasVenda());
//    }
//
//}
