package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation;

import br.com.apirest.leadersofts.leadcapture.core.FindLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate.SessionResolver;
import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.IUpdaterHandlerFactory;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.DatesUpdaterImpl;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.LeadUtilsUpdateFactoryImpl;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.IUpdater;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.jpa.LeadRepository;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.jpa.impl.LeadRepositoryCustomQueryImpl;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static br.com.apirest.leadersofts.leadcapture.infrastructure.repository.jpa.impl.LeadRepositoryCustomQueryImpl.getLead;

@Component
public class UpdaterImpl implements IUpdater, IUpdaterHandlerFactory {

    public static UpdaterImpl INSTANCE;

    private final DatesUpdaterImpl datesHandler;

    private final LeadUtilsUpdateFactoryImpl factory;

    private final LeadRepository leadRepository;

    private final FindLeadUseCase findLeadUseCase;

    private SessionResolver sessionResolver;

    private LeadConverter converter;

    private LeadRepositoryCustomQueryImpl  leadRepositoryCustomQuery;

    public UpdaterImpl(DatesUpdaterImpl datesHandler, LeadUtilsUpdateFactoryImpl factory, LeadRepository leadRepository, FindLeadUseCase findLeadUseCase,
                       SessionResolver sessionResolver, LeadConverter converter, LeadRepositoryCustomQueryImpl leadRepositoryCustomQuery) {
        this.datesHandler = datesHandler;
        this.factory = factory;
        this.leadRepository = leadRepository;
        this.findLeadUseCase = findLeadUseCase;
        this.sessionResolver = sessionResolver;
        this.converter = converter;
        this.leadRepositoryCustomQuery = leadRepositoryCustomQuery;
    }

    @Override
    public Mono<LeadRecord> update(Lead lead) {

        AtomicReference<Lead> leadToUpdate = new AtomicReference<>(new Lead());
        
        findLeadByIdToUpdate(lead)
                .switchIfEmpty(Mono.error(new Exception("Lead not found") ))
                .map( e -> {
                    prepareToUpdate(lead, e);
                    leadToUpdate.set(e);
                    return e;
        })
        .flatMap(leadRepository::save)
        .subscribe();

        return Mono.just(LeadConverter.getLeadRecord(leadToUpdate.get()));
    }

    private void prepareToUpdate(Lead lead, Lead leadToUpdate) {
        this.setBasics(lead, leadToUpdate);
        this.setCarInfo(leadToUpdate, lead);
        this.setContactAndAndressInfo(leadToUpdate, lead);
        factory.transformLowerCaseInUpperCaseBeforeUpdate(leadToUpdate);
        leadToUpdate.setNewEntry(false);
    }

    @Override
    public void setBasics(Lead lead, Lead leadToUpdate) {
        leadToUpdate.setId(lead.getId());
        leadToUpdate.setNome(lead.getNome());
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
                UpdaterImpl.INSTANCE.leadRepository,UpdaterImpl.INSTANCE.findLeadUseCase, UpdaterImpl.getInstance().sessionResolver,
                UpdaterImpl.INSTANCE.converter, UpdaterImpl.INSTANCE.leadRepositoryCustomQuery) : INSTANCE;
    }

    private Flux<Lead> findLeadByIdToUpdate(Lead lead) {

        var query = leadRepositoryCustomQuery.getCustomQuery().toString().concat(" WHERE ").concat("L.id =  " + lead.getId());

        var result = sessionResolver.getSessionFactory().withStatelessSession( session ->
                session.createNativeQuery(query)
                        .getResultList()
                        .invoke(leads -> leads.stream().forEach(System.out::println))
        ).await().indefinitely();

        List<Lead> leads = new ArrayList();

        result.stream().
                forEach( row -> leads.add(getLead((Object[]) row)));

        return Flux.fromIterable(leads);
    }

}
