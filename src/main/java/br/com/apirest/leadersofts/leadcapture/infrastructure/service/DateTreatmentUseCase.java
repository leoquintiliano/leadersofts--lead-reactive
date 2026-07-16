package br.com.apirest.leadersofts.leadcapture.infrastructure.service;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.DatasRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Component
@AllArgsConstructor
public class DateTreatmentUseCase {


    public DatasRecord getPersistableDatasRecord(LeadRecord leadDTO, Lead lead ) {
        var dataNascimento = (nonNull(leadDTO.dataNascimento()) && !leadDTO.dataNascimento().isEmpty()) ? this.formatDate(leadDTO.dataNascimento()) : "";
        var ultimoContato = (nonNull(leadDTO.ultimoContato()) && !leadDTO.ultimoContato().isEmpty()) ? this.formatDate(leadDTO.ultimoContato()) : "";
        var primeiroContato = (nonNull(leadDTO.primeiroContato()) && !leadDTO.primeiroContato().isEmpty()) ? this.formatDate(leadDTO.primeiroContato()) : ""; // 20/06/2023
        var dataVenda = (nonNull(leadDTO.dataVenda()) && !leadDTO.dataVenda().isEmpty()) ? this.formatDate(leadDTO.dataVenda()) : "";

        dataNascimento = dataNascimento.indexOf("/") == 4 ? this.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        ultimoContato = ultimoContato.indexOf("/") == 4 ? this.formatDateToBrazilianLocale(lead.getUltimoContato()) : ultimoContato;
        primeiroContato = primeiroContato.indexOf("/") == 4 ? this.formatDateToBrazilianLocale(lead.getPrimeiroContato()) : primeiroContato;
        dataVenda = dataVenda.indexOf("/") == 4 || dataVenda.indexOf("-") == 4  ? this.formatDateToBrazilianLocale(lead.getDataVenda()) : dataVenda;

        return new DatasRecord(dataNascimento,ultimoContato,primeiroContato,dataVenda);
    }

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

    public static String formatDate(String date) {
        if(Objects.nonNull(date) && (date.indexOf("-") == 2 || date.indexOf("-") == 5 || date.indexOf("/") == 2 || date.indexOf("-") == 5) ) {
            var year = date.substring(0,2);
            var month = date.substring(3,5);
            var day = date.substring(6,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        if(Objects.nonNull(date) && date.length() > 4) { // 16-10-2023  2023-10-16
            var year = date.substring(0,4);
            var month = date.substring(5,7);
            var day = date.substring(8,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        return "";
    }

    public static String formatDateToBrazilianLocale(String date) {
        if(Objects.nonNull(date) && date.length() > 4 && (date.indexOf("/") == 4 || date.indexOf("-") == 4) ) { // 16-10-2023  2023-10-16
            var year = date.substring(0,4);
            var month = date.substring(5,7);
            var day = date.substring(8,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        return date;
    }

    public static void treatBirthDate(String dataNascimento, Lead lead) {
        var birthDate = dataNascimento.indexOf("/") == 4 ? formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        lead.setDataNascimento(birthDate);
    }

    public static List<Long> getDiasCadastro(String primeiroContato, String ultimoContato, String dataVenda) {

        var diasCadastro = getDifferenceOfDays(primeiroContato);
        var diasUltimoContato = getDifferenceOfDays(ultimoContato);
        var diasVenda = getDifferenceOfDays(dataVenda);
        List<Long> calculatedDays = new ArrayList<>();
        calculatedDays.add(diasCadastro);
        calculatedDays.add(diasUltimoContato);
        calculatedDays.add(diasVenda);
        return calculatedDays;
    }

    public static Long getDifferenceOfDays(String registryDate) {
        var currentDate = LocalDate.now();
        return getDifferenceValue(registryDate, currentDate);
    }

    private static Long getDifferenceValue(String registryDate, LocalDate currentDate) {
        var resultOfDifferenceOfDaysSinceRegistered = 0L;
        if(registryDate.length() == 10) {
            boolean isBrasilianGMT = registryDate.indexOf("/") == 2 ? true : false;
            if(isBrasilianGMT) {
                var year  = registryDate.substring(6,registryDate.length());
                var month = registryDate.substring(3,5);
                var day  = registryDate.substring(0,2);
                var usaGMTFormatDate = year.concat("/").concat(month).concat("/").concat(day);
                return resultOfDifferenceOfDaysSinceRegistered = getDifference(usaGMTFormatDate,currentDate);
            }
            return resultOfDifferenceOfDaysSinceRegistered = getDifference(registryDate,currentDate);
        }
        return resultOfDifferenceOfDaysSinceRegistered;
    }

    public static Long getDifference(String registryDate,LocalDate currentDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        var contextParamDate = LocalDate.parse(registryDate,dateTimeFormatter);
        contextParamDate.datesUntil(currentDate);
        return contextParamDate.datesUntil(currentDate).collect(Collectors.toList()).stream().count();
    }

    public static List<Long> getDatasCalculadas(Lead lead) {
        var primeiroContato = getFormattedDates(lead).get(0);
        var ultimoContato =   getFormattedDates(lead).get(1);
        var dataVenda = getFormattedDates(lead).get(2);
        return getDiasCadastro(primeiroContato,ultimoContato,dataVenda);
    }

    private static List<String> getFormattedDates(Lead lead) {

        return  Stream.of(formatDate(lead.getPrimeiroContato()),
                        formatDate(lead.getUltimoContato()),
                        formatDate((lead.getDataVenda()))
                )
                .collect(Collectors.toList());
    }
    
    
}
