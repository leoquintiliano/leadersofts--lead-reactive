package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.DatesUpdaterFactory;
import br.com.apirest.leadersofts.leadcapture.infrastructure.service.DateTreatmentUseCase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DatesUpdaterImpl implements DatesUpdaterFactory {

    private LeadConverter mapper;

    private DateTreatmentUseCase dateService;

    public DatesUpdaterImpl(LeadConverter mapper, DateTreatmentUseCase dateService) {
        this.mapper = mapper;
        this.dateService = dateService;
    }

    @Override
    public void formatDateFields(Lead leadToUpdate, Lead lead) {
        var dataNascimento = this.mapper.formatDate(lead.getDataNascimento());
        var ultimoContato = this.mapper.formatDate(lead.getUltimoContato());
        var primeiroContato = this.mapper.formatDate(lead.getPrimeiroContato());

        dataNascimento = dataNascimento.indexOf("/") == 4 ? this.mapper.formatDateToBrazilianLocale(lead.getDataNascimento()) : dataNascimento;
        ultimoContato = ultimoContato.indexOf("/") == 4 ? this.mapper.formatDateToBrazilianLocale(lead.getUltimoContato()) : ultimoContato;
        primeiroContato = primeiroContato.indexOf("/") == 4 ? this.mapper.formatDateToBrazilianLocale(lead.getPrimeiroContato()) : primeiroContato;

        if(lead.getDataNascimento().length() > 6)
            leadToUpdate.setDataNascimento(dataNascimento);
        if(lead.getPrimeiroContato().length() > 6)
            leadToUpdate.setPrimeiroContato(primeiroContato);
        if(lead.getUltimoContato().length() > 6)
            leadToUpdate.setUltimoContato(ultimoContato);
    }

    @Override
    public void prepareDatesToUpdte(Lead leadToUpdate, Lead lead) {
        List<Long> datesAfterCalculated = new ArrayList<>();
        this.updateDatesIfNecessary(lead, datesAfterCalculated);
        leadToUpdate.setDiasUltimoContato(datesAfterCalculated.get(0));
        leadToUpdate.setDiasCadastro(datesAfterCalculated.get(1));
        leadToUpdate.setDataVenda(Objects.nonNull(lead.getDataVenda()) ? lead.getDataVenda() : "");
    }

    @Override
    public void updateDatesIfNecessary(Lead leadToUpdate, List<Long> calculatedDates) {
        var differenceOfDaysSinceLastContact = 0L;
        var differenceOfDaysSinceRegistered = 0L;
        if (leadToUpdate.getDiasUltimoContato() == 0) {
            var ultimoContatoDia = dateService.isValidDate(leadToUpdate.getUltimoContato()) ? this.mapper.formatDate(leadToUpdate.getUltimoContato()) : LocalDate.now().toString();
            ultimoContatoDia = ultimoContatoDia.replace("-", "/");
            differenceOfDaysSinceLastContact = dateService.differenceOfDays(ultimoContatoDia);
            calculatedDates.add(differenceOfDaysSinceLastContact);
        } else{
            calculatedDates.add(0L);
        }
        if (leadToUpdate.getDiasCadastro() == 0) {
            var dataCadastro = dateService.isValidDate(leadToUpdate.getDataCadastro())  ? this.mapper.formatDate(leadToUpdate.getDataCadastro()) : LocalDate.now().toString();
            dataCadastro = dataCadastro.replace("-", "/");
            differenceOfDaysSinceRegistered = dateService.differenceOfDays(dataCadastro);
            calculatedDates.add(differenceOfDaysSinceRegistered);
        } else {
            calculatedDates.add(0L);
        }
    }

}
