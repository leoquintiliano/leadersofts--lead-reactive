package br.com.apirest.leadersofts.leadcapture.infrastructure.converter;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.exception.LeadExceptions;
import br.com.apirest.leadersofts.leadcapture.infrastructure.mapper.LeadMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LeadConverter {

    public Lead getLead(LeadRecord leadRecord) {
        return LeadMapper.INSTANCE.leadRecordToEntity(leadRecord);
    }

//TODO-LEANDRO refatorar aqui e tentar parar de ferir os príncipios SOLID, a single responsibility aqui nem existe

    public static LeadRecord getLeadRecord(Lead lead) {

        var datasCalculadas = getDatasCalculadas(lead);
        var diasCadastro = Objects.nonNull(lead.getDiasCadastro()) && datasCalculadas.get(0) >= lead.getDiasCadastro() ? datasCalculadas.get(0) : lead.getDiasCadastro();
        var diasUltimoContato = Objects.nonNull(lead.getDiasUltimoContato()) && datasCalculadas.get(1) >= lead.getDiasUltimoContato() ? datasCalculadas.get(1) : lead.getDiasUltimoContato();
        var diasVenda = Objects.nonNull(lead.getDiasVenda()) && datasCalculadas.get(2) >= lead.getDiasVenda() ? datasCalculadas.get(2) : lead.getDiasVenda();

        treatBirthDate(formatDate(lead.getDataNascimento()), lead);

        var primeiroContato = formatDate(lead.getPrimeiroContato());
        var ultimoContato =   formatDate(lead.getUltimoContato());
        var dataNascimento = formatDate(lead.getDataNascimento());
        var dataVenda = formatDate(lead.getDataVenda());

        dataNascimento = dataNascimento.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        ultimoContato = ultimoContato.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getUltimoContato()) : ultimoContato;
        primeiroContato = primeiroContato.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getPrimeiroContato()) : primeiroContato;

        return new LeadRecord(lead.getId(), lead.getNome(),primeiroContato, ultimoContato,
                dataNascimento,lead.getCelular(),lead.getCelular2(), lead.getTelefone(),lead.getEndereco(),lead.getEmail(),lead.getUf(),lead.getCidade(),
                lead.getCarroInteresse1(),lead.getCarroInteresse2(),lead.getCarroInteresse3(),
                lead.getCarroAtual1(),lead.getCarroAtual2(),lead.getCarroAtual3(), lead.getVendedor(),lead.getStatus(),lead.getOpcaoVeiculo(), lead.getObservacoes(),
                lead.getDataCadastro(), diasCadastro, diasUltimoContato, dataVenda,diasVenda);
    }

    public static Lead getLeadModel(Lead lead) {
        var datasCalculadas = getDatasCalculadas(lead);
        var diasCadastro = Objects.nonNull(lead.getDiasCadastro()) && datasCalculadas.get(0) >= lead.getDiasCadastro() ? datasCalculadas.get(0) : lead.getDiasCadastro();
        var diasUltimoContato = Objects.nonNull(lead.getDiasUltimoContato()) && datasCalculadas.get(1) >= lead.getDiasUltimoContato() ? datasCalculadas.get(1) : lead.getDiasUltimoContato();
        var diasVenda = Objects.nonNull(lead.getDiasVenda()) && datasCalculadas.get(2) >= lead.getDiasVenda() ? datasCalculadas.get(2) : lead.getDiasVenda();

        treatBirthDate(formatDate(lead.getDataNascimento()), lead);

        var primeiroContato = formatDate(lead.getPrimeiroContato());
        var ultimoContato =   formatDate(lead.getUltimoContato());
        var dataNascimento = formatDate(lead.getDataNascimento());
        var dataVenda = formatDate(lead.getDataVenda());

        dataNascimento = dataNascimento.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        ultimoContato = ultimoContato.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getUltimoContato()) : ultimoContato;
        primeiroContato = primeiroContato.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getPrimeiroContato()) : primeiroContato;

        return lead;
    }

    public static LeadRecord getLeadRecordResponse(Lead lead) {
        return new LeadRecord(lead.getId(), lead.getNome(), lead.getPrimeiroContato(), lead.getUltimoContato(), lead.getDataNascimento(),
                lead.getCelular(), lead.getCelular2(), lead.getTelefone(), lead.getEndereco(), lead.getEmail(), lead.getUf(), lead.getCidade(),
                lead.getCarroInteresse1(), lead.getCarroInteresse2(),lead.getCarroInteresse3(), lead.getCarroAtual1(), lead.getCarroAtual2(),
                lead.getCarroAtual3(), lead.getVendedor(), lead.getStatus(), lead.getOpcaoVeiculo(), lead.getObservacoes(),
                lead.getDataCadastro(), lead.getDiasCadastro(), lead.getDiasUltimoContato(), lead.getDataVenda(), lead.getDiasVenda());
    }


//    public static LeadRecord getLeadRecord(Lead leadRef) {
//
//        var datasCalculadas = getDatasCalculadas(leadRef);
//        var diasCadastro = Objects.nonNull(leadRef.getDiasCadastro()) && datasCalculadas.get(0) >= leadRef.getDiasCadastro() ? datasCalculadas.get(0) : leadRef.getDiasCadastro();
//        var diasUltimoContato = Objects.nonNull(leadRef.getDiasUltimoContato()) && datasCalculadas.get(1) >= leadRef.getDiasUltimoContato() ? datasCalculadas.get(1) : leadRef.getDiasUltimoContato();
//        var diasVenda = Objects.nonNull(leadRef.getDiasVenda()) && datasCalculadas.get(2) >= leadRef.getDiasVenda() ? datasCalculadas.get(2) : leadRef.getDiasVenda();
//
//        treatBirthDate(formatDate(leadRef.getDataNascimento()), leadRef);
//
//        var newLead = getNewLead(leadRef, Objects.nonNull(datasCalculadas.get(0)) ? datasCalculadas.get(0) : diasCadastro,
//                Objects.nonNull(datasCalculadas.get(1)) ? datasCalculadas.get(1) : diasUltimoContato, Objects.nonNull(datasCalculadas.get(2)) ? datasCalculadas.get(2) : diasVenda);
//
//        return modelMapper.modelMapper().map(newLead,LeadRecord.class);
//    }

    private static List<String> getFormattedDates(Lead lead) {

        return  Stream.of(formatDate(lead.getPrimeiroContato()),
                        formatDate(lead.getUltimoContato()),
                        formatDate((lead.getDataVenda()))
                )
                .collect(Collectors.toList());
    }

    private static List<Long> getDatasCalculadas(Lead lead) {
        var primeiroContato = getFormattedDates(lead).get(0);
        var ultimoContato =   getFormattedDates(lead).get(1);
        var dataVenda = getFormattedDates(lead).get(2);
        return LeadConverter.getDiasCadastro(primeiroContato,ultimoContato,dataVenda);
    }

    private static void treatBirthDate(String dataNascimento, Lead lead) {
        var birthDate = dataNascimento.indexOf("/") == 4 ? LeadConverter.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
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

    private static Lead getNewLead(Lead lead, Long diasCadastro, Long diasUltimoContato, Long diasVenda) {
        return Lead.builder()
                .id(Objects.nonNull(lead.getId()) ? lead.getId() : 0L)
                .nome(Objects.nonNull(lead.getNome()) ? lead.getNome() : "" )
                .telefone(Objects.nonNull(lead.getTelefone()) ? lead.getTelefone() : "")
                .dataNascimento(Objects.nonNull(lead.getDataNascimento()) ? lead.getDataNascimento() : "")
                .primeiroContato(Objects.nonNull(lead.getPrimeiroContato()) ? lead.getPrimeiroContato() : "")
                .ultimoContato(Objects.nonNull(lead.getUltimoContato()) ? lead.getUltimoContato() : "")
                .carroAtual1(Objects.nonNull(lead.getCarroAtual1()) ? lead.getCarroAtual1() : "")
                .carroAtual2(Objects.nonNull(lead.getCarroAtual2()) ? lead.getCarroAtual2() : "")
                .carroAtual3(Objects.nonNull(lead.getCarroAtual3()) ? lead.getCarroAtual3() : "")
                .carroInteresse1( Objects.nonNull(lead.getCarroInteresse1()) ? lead.getCarroInteresse1() : "")
                .carroInteresse2( Objects.nonNull(lead.getCarroInteresse2()) ? lead.getCarroInteresse2() : "")
                .carroInteresse3( Objects.nonNull(lead.getCarroInteresse3()) ? lead.getCarroInteresse3() : "")
                .celular( Objects.nonNull(lead.getCelular()) ? lead.getCelular() : "")
                .celular2( Objects.nonNull(lead.getCelular2()) ? lead.getCelular2(): "")
                .uf( Objects.nonNull(lead.getUf()) ? lead.getUf() : "")
                .cidade( Objects.nonNull(lead.getCidade()) ? lead.getCidade() : "")
                .vendedor( Objects.nonNull(lead.getVendedor()) ? lead.getVendedor() : "")
                .status( Objects.nonNull(lead.getStatus()) ? lead.getStatus() : "")
                .opcaoVeiculo(Objects.nonNull(lead.getOpcaoVeiculo()) ? lead.getOpcaoVeiculo() : "")
                .observacoes(Objects.nonNull(lead.getObservacoes()) ? lead.getObservacoes() : "")
                .email(Objects.nonNull(lead.getEmail()) ? lead.getEmail() : "")
                .endereco(Objects.nonNull(lead.getEndereco()) ? lead.getEndereco() : "")
                .diasCadastro(diasCadastro)
                .diasUltimoContato(diasUltimoContato)
                .diasVenda(diasVenda)
                .dataCadastro(Objects.nonNull(lead.getDataCadastro()) ? lead.getDataCadastro() : "")
                .dataVenda(Objects.nonNull(lead.getDataVenda()) ? lead.getDataVenda() : "")
                .build();
    }

    public Mono<LeadRecord> getMonoRecordFromLeadMono(Mono<Lead> leadMono) {
        return leadMono
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .doOnNext(System.out::println)
                .switchIfEmpty(LeadExceptions.unreachableLeadException("An error occurred while trying to convert lead to record!"));
    }

    public LeadRecord getLeadRecord(Mono<Lead> leadMono) {

        AtomicReference<LeadRecord> leadDTO = new AtomicReference();

        leadMono
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .subscribe( leadDTO::set);

        return leadDTO.get();
    }

}
