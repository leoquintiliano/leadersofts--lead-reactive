package br.com.apirest.leadersofts.leadcapture.infrastructure.factory.implementation.datasource;

import br.com.apirest.leadersofts.leadcapture.infrastructure.converter.LeadConverter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.exception.LeadExceptions;
import br.com.apirest.leadersofts.leadcapture.infrastructure.exception.NotFoundException;
import br.com.apirest.leadersofts.leadcapture.infrastructure.factory.find.IFinderHandler;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.mapper.LeadMapper;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.LeadRepository;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.LeadRepositoryCustomQuery;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Component
public class FinderHandlerDataSource implements IFinderHandler {

    private static FinderHandlerDataSource handler;

    private static LeadRepository repository;

    private static LeadRepositoryCustomQuery leadRepositoryCustomQuery;

    private static final Logger LOGGER = Logger.getLogger(FinderHandlerDataSource.class.getName());

    public FinderHandlerDataSource(LeadRepository repository, LeadRepositoryCustomQuery leadRepositoryCustomQuery) {
        this.repository = repository;
        this.leadRepositoryCustomQuery = leadRepositoryCustomQuery;
    }

    @Override
    public Flux<LeadRecord> findByType(String term, String type) {
        switch(type) {
            case "id" -> { return findById(term).flux(); }
            case "name" -> { return findLeadsByName(term); }
            case "birthday" -> { return findLeadsByBirthday(term); }
            case "phone" -> {return findByPhone(term).flux() ; }
            case "mobile" -> { return  findByMobile(term).flux(); }
            default -> throw new NotFoundException("None [Lead] was found!");
        }
    }

    @Override
    public Flux<LeadRecord> findLeadsById(List<Long> filteredIds) {
        return repository.findAllById(filteredIds)
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO);
    }

    @Override
    public Flux<LeadRecord> findLeadsByName(String name) {
        return this.repository.findByNome(name)
                .map(l -> LeadMapper.INSTANCE.leadToLeadRecordDTO(l) )
                .doOnNext(l -> System.out.println(l.nome()))
                .switchIfEmpty(LeadExceptions.unreachableLeadException("Lead could not be found on database!"));
    }

    @Override
    public Flux<LeadRecord> findAll() {
        return this.repository.findAll()
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .switchIfEmpty(LeadExceptions.unreachableLeadException("No Leads found!"));
    }

    @Override
    public Flux<LeadRecord> findLeadsByBirthday(String birthDate) {
        return this.repository.findByBirthday(birthDate.replace("-","/"))
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .switchIfEmpty(LeadExceptions.unreachableLeadException("No Leads found!"));
    }

    @Override
    public Flux<LeadRecord> findLeadsWithFilter(LeadFilter filter) {
        LOGGER.info("BUSCANDO POR LEADS... :: " + filter);
        return this.leadRepositoryCustomQuery.findWithFilter(filter)
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO);
    }

    private Mono<LeadRecord> findById(String term) {
        AtomicReference<LeadRecord> leadDTO = new AtomicReference();
        var response = repository.findById(Long.parseLong(term)).switchIfEmpty(Mono.error( () -> new RuntimeException("Lead not found!") ) );
        response.subscribe( lead -> leadDTO.set(LeadConverter.getLeadRecord(lead)) );
        return Mono.just(leadDTO.get());
    }
    private Mono<LeadRecord> findByPhone(String term) {
        return repository.findByTelefone(term)
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .switchIfEmpty(Mono.error( () -> new RuntimeException("Lead not found!") ));
    }

    private Mono<LeadRecord> findByMobile(String term) {
        return repository.findByCelular(term)
                .map(LeadMapper.INSTANCE::leadToLeadRecordDTO)
                .switchIfEmpty(Mono.error( () -> new RuntimeException("Lead not found!") ) );
    }

    public Flux<LeadRecord> findByAnyOfTheseFilters(Long id, String nome, String celular, String telefone) {
        if (id != null && id > -1) {
            return repository.findById(id).map(LeadConverter::getLeadRecord).flux();
        }
        if (!nome.isBlank() && !nome.equals("-") && Objects.nonNull(nome)) {
            return Flux.from(repository.findByNome(nome)).map(LeadMapper.INSTANCE::leadToLeadRecordDTO);
//            return Flux.from(repository.findByNome(nome).map(LeadConverter::getLeadRecord));
        }
        if (!celular.isBlank() && !celular.equals("-") && Objects.nonNull(celular)) {
            return findByMobile(telefone).flux();
        }
        if (!telefone.isBlank() && !telefone.equals("-") && Objects.nonNull(telefone)) {
            return this.findByPhone(telefone).flux();
        }
        return Flux.just( LeadConverter.getLeadRecord(Lead.builder().build()));
    }

    public static synchronized FinderHandlerDataSource getInstance() {
        return Objects.isNull(handler) ? new FinderHandlerDataSource(FinderHandlerDataSource.repository, FinderHandlerDataSource.leadRepositoryCustomQuery) : handler;
    }

}
