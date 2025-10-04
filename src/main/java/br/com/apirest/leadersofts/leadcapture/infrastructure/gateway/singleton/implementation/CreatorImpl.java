package br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate.SessionResolver;
import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.LeadUtilsCreateFactoryImpl;
import br.com.apirest.leadersofts.leadcapture.infrastructure.gateway.singleton.ICreator;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.LeadRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class CreatorImpl implements ICreator {

    private static CreatorImpl uniqueInstance;

    @Autowired
    private LeadUtilsCreateFactoryImpl factory;

    @Autowired
    private LeadRepository repository;

    @Autowired
    private LeadConverter converter;

    @Autowired
    private SessionResolver sessionResolver;


    @Override
    public Mono<LeadRecord> create(LeadRecord leadDTO) {

        //TODO-LEANDRO: all the save logic goes here
        var lead = converter.getLead(leadDTO);
        factory.prepareFieldsBeforeSave(leadDTO,lead);

         var maxId = this.getLastPersistedLeadId();

         if(Objects.nonNull(maxId)) {
             lead.setId(maxId + 1);
             repository.save(lead).subscribe();
         }

        return converter.getMonoRecordFromLeadMono(Mono.just(lead));
    }

    public static synchronized CreatorImpl getInstance() {
        if(Objects.isNull(uniqueInstance)) {
            uniqueInstance = new CreatorImpl();
        }
        return uniqueInstance;
    }

    private Long getLastPersistedLeadId() {
        return sessionResolver.getSessionFactory().withStatelessSession( session ->
                        session.createNativeQuery("SELECT MAX(l.id) FROM lead l", Long.class)
                        .getResultList()
                        .invoke(leads -> leads.stream().forEach(System.out::println))
                ).await().indefinitely()
                .stream()
                .map( row -> Long.valueOf(row.toString()))
                .collect(Collectors.toList())
                .getLast();
    }

}
