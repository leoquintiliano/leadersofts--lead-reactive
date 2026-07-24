package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.controller.LeadController;
import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.LeadUtilsCreateFactory;
import br.com.apirest.leadersofts.leadcapture.infrastructure.mapper.LeadMapper;
import br.com.apirest.leadersofts.leadcapture.infrastructure.service.DateTreatmentUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.utils.implementation.UtilMethodsImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Objects.nonNull;

@Component
public class LeadUtilsCreateFactoryImpl extends LeadUtilsBaseFactoryImpl implements LeadUtilsCreateFactory {

    private UtilMethodsImpl utilMethods = null;

    private final DateTreatmentUseCase  dateService;

    private final LeadConverter leadConverter;

    public LeadUtilsCreateFactoryImpl(UtilMethodsImpl utilMethods, DateTreatmentUseCase dateService,
                                      LeadMapper leadMapper, LeadConverter leadConverter) {
        this.utilMethods = utilMethods;
        this.dateService = dateService;
        this.leadConverter = leadConverter;
    }

    public void prepareFieldsBeforeSave(LeadRecord leadDTO, Lead lead) {
        this.prepareDatesToSave(leadDTO, lead);
        this.preparePhoneBeforePersist(leadDTO, lead);
        this.convertLeadCarInfoIntoUpperCase(lead);
        this.convertLeadBasicInfoIntoUpperCase(lead);
        this.convertLeadExtraInformationIntoUpperCase(lead);
    }

    @Override
    public void prepareDatesToSave(LeadRecord leadDTO, Lead lead) {

        var datasTratadas = dateService.getPersistableDatasRecord(leadDTO,lead);

        var dataCadastro = ((nonNull(leadDTO.dataCadastro()) && !leadDTO.dataCadastro().isEmpty()) && dateService.isValidDate(leadDTO.dataCadastro())) ?
                this.dateService.formatDate(leadDTO.dataCadastro()) : LocalDate.now().toString();
        dataCadastro = dataCadastro.replace("-", "/");

        var differenceOfDaysSinceRegistered = dateService.differenceOfDays(dataCadastro);  // this.utilMethods.differenceOfDays(dataCadastro);

        var ultimoContatoDia =  (nonNull(leadDTO.ultimoContato()) &&  dateService.isValidDate(leadDTO.ultimoContato())) ?
                this.dateService.formatDate(leadDTO.ultimoContato()) : LocalDate.now().toString();

        ultimoContatoDia = ultimoContatoDia.replace("-", "/");
        var differenceOfDaysSinceLastContact = dateService.differenceOfDays(ultimoContatoDia);

        setLeadDates(lead, differenceOfDaysSinceRegistered, differenceOfDaysSinceLastContact, datasTratadas.dataNascimento(), datasTratadas.primeiroContato(), datasTratadas.ultimoContato(), datasTratadas.dataCadastro() );
    }

    private static void setLeadDates(Lead lead, long differenceOfDaysSinceRegistered, long differenceOfDaysSinceLastContact, String dataNascimento, String primeiroContato, String ultimoContato, String dataVenda) {
        lead.setDiasCadastro(nonNull(differenceOfDaysSinceRegistered) ? differenceOfDaysSinceRegistered : 0);
        lead.setDiasUltimoContato(nonNull(differenceOfDaysSinceLastContact) ? differenceOfDaysSinceLastContact : 0L);
        lead.setDataNascimento((nonNull(dataNascimento) && !dataNascimento.isEmpty()) ? dataNascimento.replace("-","/") : "");
        lead.setPrimeiroContato((nonNull(primeiroContato) && !primeiroContato.isEmpty()) ? primeiroContato.replace("-","/") : "");
        lead.setUltimoContato((nonNull(ultimoContato) && !ultimoContato.isEmpty()) ? ultimoContato.replace("-","/") : "");
        lead.setDataVenda((nonNull(dataVenda) && !dataVenda.isEmpty()) ? dataVenda.replace("-","/") : "");
    }

    @Override
    public void preparePhoneBeforePersist(LeadRecord leadDTO, Lead lead) {

        //TODO-LEANDRO testar esse ao invés do comentado abaixo
        String formattedPhone = getFormatedPhoneNumber.apply(leadDTO, nonNull(leadDTO.telefone()));
        String formattedMobile = getFormatedPhoneNumber.apply(leadDTO, nonNull(leadDTO.telefone()));
        String formattedMobile2 = getFormatedPhoneNumber.apply(leadDTO, nonNull(leadDTO.telefone()));

//        var formattedPhone =  nonNull(leadDTO.telefone()) ? this.utilMethods.getPhoneAccordingToRegex(leadDTO.telefone()) : "99999999999";
//        var formattedMobile = nonNull(leadDTO.celular()) ? this.utilMethods.getPhoneAccordingToRegex(leadDTO.celular()) : "99999999999";
//        var formattedMobile2 = nonNull(leadDTO.celular2()) ? this.utilMethods.getPhoneAccordingToRegex(leadDTO.celular2()) : "9999999999";

//        BiFunction<LeadRecord,Lead,Lead> leadItem = (dto,domain) -> leadConverter.getLead(dto);

        Function<LeadRecord,Lead> leadFunction = leadRecord -> leadConverter.getLead(leadRecord);
        leadFunction.apply(leadDTO);

        lead.setTelefone(formattedPhone);
        lead.setCelular(formattedMobile);
        lead.setCelular2(formattedMobile2);
    }

    private BiFunction<LeadRecord,Boolean,String> getFormatedPhoneNumber = (lead, elegible) ->
            elegible ? this.utilMethods.getPhoneAccordingToRegex(lead.telefone()) : "99999999999";


}
