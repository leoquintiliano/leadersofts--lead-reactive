package br.com.apirest.leadersofts.leadcapture.infrastructure.mapper;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface LeadMapper {

    LeadMapper INSTANCE = Mappers.getMapper(LeadMapper.class);

    @Mapping(source = "dataVenda", target = "dataVenda", qualifiedByName = "prepareData")
    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "prepareData")
    @Mapping(source = "dataCadastro", target = "dataCadastro", qualifiedByName = "prepareData")
    @Mapping(source = "primeiroContato", target = "primeiroContato", qualifiedByName = "prepareData")
    @Mapping(source = "ultimoContato", target = "ultimoContato", qualifiedByName = "prepareData")
    LeadRecord leadToLeadRecordDTO(Lead lead);

    @Mapping(source = "dataVenda", target = "dataVenda", qualifiedByName = "prepareData")
    @Mapping(source = "dataNascimento", target = "dataNascimento", qualifiedByName = "prepareData")
    @Mapping(source = "dataCadastro", target = "dataCadastro", qualifiedByName = "prepareData")
    @Mapping(source = "primeiroContato", target = "primeiroContato", qualifiedByName = "prepareData")
    @Mapping(source = "ultimoContato", target = "ultimoContato", qualifiedByName = "prepareData")
    Lead leadRecordToEntity(LeadRecord leadRecord);

    @Named("prepareData")
    default String prepareData(String value) {
        if (Objects.nonNull(value) && value.indexOf("/") == 2) {
            var date = LocalDate.parse(value.replaceAll("/","-"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date);
        }
        return null;
    }

}
