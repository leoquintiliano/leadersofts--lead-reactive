package br.com.apirest.leadersofts.leadcapture.infrastructure.converter;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadDTO;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.exception.LeadExceptions;
import br.com.apirest.leadersofts.leadcapture.infrastructure.mapper.LeadMapper;
import br.com.apirest.leadersofts.leadcapture.infrastructure.service.DateTreatmentUseCase;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class LeadConverter {

    private static DateTreatmentUseCase dateService;

    public LeadConverter(DateTreatmentUseCase dateService) {
        this.dateService = dateService;
    }

    public static Lead getLead(LeadRecord leadRecord) {
        return LeadMapper.INSTANCE.leadRecordToEntity(leadRecord);
    }

    public static LeadRecord getLeadRecord(Lead lead) {

        var datasCalculadas = dateService.getDatasCalculadas(lead);
        var diasCadastro = Objects.nonNull(lead.getDiasCadastro()) && datasCalculadas.get(0) >= lead.getDiasCadastro() ? datasCalculadas.get(0) : lead.getDiasCadastro();
        var diasUltimoContato = Objects.nonNull(lead.getDiasUltimoContato()) && datasCalculadas.get(1) >= lead.getDiasUltimoContato() ? datasCalculadas.get(1) : lead.getDiasUltimoContato();
        var diasVenda = Objects.nonNull(lead.getDiasVenda()) && datasCalculadas.get(2) >= lead.getDiasVenda() ? datasCalculadas.get(2) : lead.getDiasVenda();

        dateService.treatBirthDate(dateService.formatDate(lead.getDataNascimento()), lead);

        var primeiroContato = dateService.formatDate(lead.getPrimeiroContato());
        var ultimoContato =   dateService.formatDate(lead.getUltimoContato());
        var dataNascimento = dateService.formatDate(lead.getDataNascimento());
        var dataVenda = dateService.formatDate(lead.getDataVenda());

        dataNascimento = dataNascimento.indexOf("/") == 4 ? dateService.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        ultimoContato = ultimoContato.indexOf("/") == 4 ? dateService.formatDateToBrazilianLocale(lead.getUltimoContato()) : ultimoContato;
        primeiroContato = primeiroContato.indexOf("/") == 4 ? dateService.formatDateToBrazilianLocale(lead.getPrimeiroContato()) : primeiroContato;

        return new LeadRecord(lead.getId(), lead.getNome(),primeiroContato, ultimoContato,
                dataNascimento,lead.getCelular(),lead.getCelular2(), lead.getTelefone(),lead.getEndereco(),lead.getEmail(),lead.getUf(),lead.getCidade(),
                lead.getCarroInteresse1(),lead.getCarroInteresse2(),lead.getCarroInteresse3(),
                lead.getCarroAtual1(),lead.getCarroAtual2(),lead.getCarroAtual3(), lead.getVendedor(),lead.getStatus(),lead.getOpcaoVeiculo(), lead.getObservacoes(),
                lead.getDataCadastro(), diasCadastro, diasUltimoContato, dataVenda,diasVenda);
    }

    public Mono<LeadRecord> getMonoRecordFromLeadMono(Mono<Lead> leadMono) {
        return leadMono
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .doOnNext(System.out::println)
                .switchIfEmpty(LeadExceptions.unreachableLeadException("An error occurred while trying to convert lead to record!"));
    }

    public static LeadRecord getLeadRecordFromCache(LinkedHashMap result) {
        var cache = result.get("key");
        if(Objects.nonNull(cache)) {
            var lead = buildLeadFromCache(cache);
            setCarroCandidato(lead, cache);
            setDadosVenda(lead, cache);
            return LeadMapper.INSTANCE.leadToLeadRecordDTO(lead);
        }

        return LeadMapper.INSTANCE.leadToLeadRecordDTO(new Lead());
    }

    public Mono<LeadRecord> getMonoRecordFromCache(Lead lead) {
        return Mono.just(LeadMapper.INSTANCE.leadToLeadRecordDTO(lead));
    }

    private static void setDadosVenda(Lead lead, Object cache) {
        lead.setVendedor(getField(cache,"vendedor"));
        lead.setStatus(getField(cache,"status"));
        lead.setOpcaoVeiculo(getField(cache,"opcaoVeiculo"));
        lead.setObservacoes(getField(cache,"observacoes"));
        lead.setDataCadastro(getField(cache,"dataCadastro"));
        lead.setDiasCadastro(getLongField(cache,"diasCadastro"));
        lead.setDiasUltimoContato(getLongField(cache,"diasUltimoContato"));
        lead.setDataVenda(getField(cache,"dataVenda"));
        lead.setDiasVenda(getLongField(cache,"diasVenda"));
    }

    private static void setCarroCandidato(Lead lead, Object cache) {
        lead.setCarroAtual1(getField(cache,"carroAtual1"));
        lead.setCarroAtual2(getField(cache,"carroAtual2"));
        lead.setCarroAtual3(getField(cache,"carroAtual3"));
        lead.setCarroInteresse1(getField(cache,"carroInteresse1"));
        lead.setCarroInteresse2(getField(cache,"carroInteresse2"));
        lead.setCarroInteresse3(getField(cache,"carroInteresse3"));
    }

    private static Lead buildLeadFromCache(Object cache) {
        return Lead.builder()
                .id( getLongField(cache,"id"))
                .nome(getField(cache,"nome") )
                .primeiroContato(getField(cache,"primeiroContato"))
                .ultimoContato(getField(cache,"ultimoContato"))
                .dataNascimento(getField(cache,"dataNascimento"))
                .celular(getField(cache,"celular"))
                .celular2(getField(cache,"celular2"))
                .telefone(getField(cache,"telefone"))
                .endereco(getField(cache,"endereco"))
                .email(getField(cache,"email"))
                .uf(getField(cache,"uf"))
                .cidade(getField(cache,"cidade"))
                .build();
    }

    private static String getField(Object cache, String key) {
        return Objects.nonNull(((LinkedHashMap) cache).get(key)) ? ((LinkedHashMap) cache).get(key).toString() : "";
    }

    private static Long getLongField(Object cache, String key) {
        return Objects.nonNull(((LinkedHashMap) cache).get(key)) ?  Long.parseLong(((LinkedHashMap) cache).get(key).toString()) : 0L;
    }

}
