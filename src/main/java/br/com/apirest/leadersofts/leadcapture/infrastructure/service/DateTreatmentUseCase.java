package br.com.apirest.leadersofts.leadcapture.infrastructure.service;

import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.DatasRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
//@RequiredArgsConstructor
@AllArgsConstructor
public class DateTreatmentUseCase {

    private LeadConverter mapper;

    public DatasRecord getPersistableDatasRecord(LeadRecord leadDTO, Lead lead ) {
        var dataNascimento = (nonNull(leadDTO.dataNascimento()) && !leadDTO.dataNascimento().isEmpty()) ? this.mapper.formatDate(leadDTO.dataNascimento()) : "";
        var ultimoContato = (nonNull(leadDTO.ultimoContato()) && !leadDTO.ultimoContato().isEmpty()) ? this.mapper.formatDate(leadDTO.ultimoContato()) : "";
        var primeiroContato = (nonNull(leadDTO.primeiroContato()) && !leadDTO.primeiroContato().isEmpty()) ? this.mapper.formatDate(leadDTO.primeiroContato()) : ""; // 20/06/2023
        var dataVenda = (nonNull(leadDTO.dataVenda()) && !leadDTO.dataVenda().isEmpty()) ? this.mapper.formatDate(leadDTO.dataVenda()) : "";

        dataNascimento = dataNascimento.indexOf("/") == 4 ? this.mapper.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        ultimoContato = ultimoContato.indexOf("/") == 4 ? this.mapper.formatDateToBrazilianLocale(lead.getUltimoContato()) : ultimoContato;
        primeiroContato = primeiroContato.indexOf("/") == 4 ? this.mapper.formatDateToBrazilianLocale(lead.getPrimeiroContato()) : primeiroContato;
        dataVenda = dataVenda.indexOf("/") == 4 || dataVenda.indexOf("-") == 4  ? this.mapper.formatDateToBrazilianLocale(lead.getDataVenda()) : dataVenda;

        return new DatasRecord(dataNascimento,ultimoContato,primeiroContato,dataVenda);
    }

    //TODO-LEANDRO TRY IT

    private static Long getDifferenceOfDaysValue(String registryDate, LocalDate currentDate) {
        Long differenceOfDaysSinceRegistered = 0L;
        if(registryDate.length() == 10) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            registryDate = (registryDate.indexOf("/") == 2 || registryDate.indexOf("-") == 2) ? registryDate.substring(6,10).concat("/")
                    .concat(registryDate.substring(3,5)).concat("/").concat(registryDate.substring(0,2)) : registryDate;
            var contextParamDate = LocalDate.parse(registryDate,dateTimeFormatter);
            return contextParamDate.datesUntil(currentDate).collect(Collectors.toList()).stream().count();
        }
        return differenceOfDaysSinceRegistered;
    }

    public Long differenceOfDays(String registryDate) {
        var currentDate = LocalDate.now();
        return this.getDifferenceOfDaysValue(registryDate, currentDate);
    }

    public boolean isValidDate(String data) { //2023/11/04
        return Objects.nonNull(data) && !data.equals("") && data.length() > 7 && (data.contains("/") || data.contains("-") ) ?
                Boolean.TRUE : Boolean.FALSE;
    }

}
