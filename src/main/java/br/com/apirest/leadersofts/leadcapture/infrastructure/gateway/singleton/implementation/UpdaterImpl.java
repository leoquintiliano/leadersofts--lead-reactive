package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation;

import br.com.apirest.leadersofts.leadcapture.core.FindLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.IUpdaterHandlerFactory;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.DatesUpdaterImpl;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.LeadUtilsUpdateFactoryImpl;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IUpdater;
import br.com.apirest.leadersofts.leadcapture.infrastructure.mapper.LeadMapper;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.LeadRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class UpdaterImpl implements IUpdater, IUpdaterHandlerFactory {

    public static UpdaterImpl INSTANCE;

    private final DatesUpdaterImpl datesHandler;

    private final LeadUtilsUpdateFactoryImpl factory;

    //TODO-LEANDRO: find usecase instead
    private final LeadRepository leadRepository;

    private final FindLeadUseCase findLeadUseCase;

    public UpdaterImpl(DatesUpdaterImpl datesHandler, LeadUtilsUpdateFactoryImpl factory, LeadRepository leadRepository, FindLeadUseCase findLeadUseCase) {
        this.datesHandler = datesHandler;
        this.factory = factory;
        this.leadRepository = leadRepository;
        this.findLeadUseCase = findLeadUseCase;
    }

    @Override
    public Mono<LeadRecord> update(Lead lead) {

        AtomicReference<Lead> leadToUpdate = new AtomicReference<>(new Lead());

        findLeadUseCase.findLeadsById(Arrays.asList(lead.getId()))
                .map(LeadMapper.INSTANCE::leadRecordToEntity)
                .subscribe(leadToUpdate::set);
//                .doOnNext(leadToUpdate::set);

//        this.leadRepository.findById(lead.getId())
//                .subscribe( lead1 -> leadToUpdate.set(lead1));

        this.setBasics(lead, leadToUpdate.get());
        this.setCarInfo(leadToUpdate.get(), lead);
        this.setContactAndAndressInfo(leadToUpdate.get(), lead);

        factory.transformLowerCaseInUpperCaseBeforeUpdate(leadToUpdate.get());

        var persistedLead = this.leadRepository.save(leadToUpdate.get());

        var res = persistedLead.subscribe( lead1 -> {
            leadToUpdate.set(lead1);
        });

        return Mono.just(LeadConverter.getLeadRecord(leadToUpdate.get()));
    }

    @Override
    public void setBasics(Lead lead, Lead leadToUpdate) {
        leadToUpdate.setId(lead.getId());
        leadToUpdate.setObservacoes(lead.getObservacoes());
        leadToUpdate.setStatus(lead.getStatus());
        leadToUpdate.setVendedor(lead.getVendedor());
        this.datesHandler.formatDateFields(leadToUpdate, lead);
        this.datesHandler.prepareDatesToUpdte(leadToUpdate, lead);

    }

    @Override
    public void setCarInfo(Lead leadToUpdate, Lead lead) {
        leadToUpdate.setCarroInteresse1(lead.getCarroInteresse1());
        leadToUpdate.setCarroInteresse2(lead.getCarroInteresse2());
        leadToUpdate.setCarroInteresse3(lead.getCarroInteresse3());
        leadToUpdate.setCarroAtual1(lead.getCarroAtual1());
        leadToUpdate.setCarroAtual2(lead.getCarroAtual2());
        leadToUpdate.setCarroAtual3(lead.getCarroAtual3());
        leadToUpdate.setOpcaoVeiculo(lead.getOpcaoVeiculo());
    }

    @Override
    public void setContactAndAndressInfo(Lead leadToUpdate, Lead lead) {
        leadToUpdate.setUf(lead.getUf());
        leadToUpdate.setCidade(lead.getCidade());
        leadToUpdate.setCelular(lead.getCelular());
        leadToUpdate.setCelular2(lead.getCelular2());
        leadToUpdate.setTelefone(lead.getTelefone());
        leadToUpdate.setEmail(lead.getEmail());
        leadToUpdate.setEndereco(lead.getEndereco());
    }

    public static synchronized UpdaterImpl getInstance() {
        return Objects.isNull(INSTANCE) ? new UpdaterImpl(UpdaterImpl.INSTANCE.datesHandler, UpdaterImpl.INSTANCE.factory,
                UpdaterImpl.INSTANCE.leadRepository,UpdaterImpl.INSTANCE.findLeadUseCase) : INSTANCE;
    }

}
