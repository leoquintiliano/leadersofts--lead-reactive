package br.com.apirest.leadersofts.leadcapture.infrastructure.controller;

import br.com.apirest.leadersofts.leadcapture.core.CreateLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.core.FindLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.core.UpdateLeadUseCase;
import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.exception.LeadExceptions;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("lead")
public class LeadController {

    private final CreateLeadUseCase leadService;

    private final FindLeadUseCase findLeadUseCase;

    private final UpdateLeadUseCase updateLeadUseCase;

    private static final Logger LOGGER = LoggerFactory.getLogger(LeadController.class.getName());


    public LeadController(CreateLeadUseCase leadService, FindLeadUseCase findLeadUseCase, UpdateLeadUseCase updateLeadUseCase) {
        this.leadService = leadService;
        this.findLeadUseCase = findLeadUseCase;
        this.updateLeadUseCase = updateLeadUseCase;
    }

    @PostMapping("save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LeadRecord> save (@RequestBody LeadRecord leadRecord) {
        var persistedLead =  this.leadService.createLead(leadRecord);
        return persistedLead;
    }

    @PutMapping
    public Mono<LeadRecord> update(@RequestBody LeadRecord leadRecord) {
        return updateLeadUseCase.update(leadRecord);
    }

    @GetMapping("/findAfterFiltered/{filteredIds}")
    public Flux<LeadRecord> findAllById(@PathVariable("filteredIds") List<Long> filteredIds) {
        return findLeadUseCase.findLeadsById(filteredIds);
    }

    @GetMapping("/findByName/{nome}")
    public Mono<ResponseEntity<Flux<LeadRecord>>> findByName(@PathVariable("nome") String nome) {
        return Mono.just(findLeadUseCase.findLeadsByName(nome))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/findByBirthday/{birthday}")
    public Flux<LeadRecord> findByBirthday(@PathVariable("birthday") String birthday) {
        return findLeadUseCase.findLeadsByBirthday(birthday);
    }

    @GetMapping("/find-filtered")
    public Flux<LeadRecord> search(@RequestBody LeadFilter filter) {
        return findLeadUseCase.findLeadsWithFilter(filter)
                .doOnNext(l -> LOGGER.info("Lead found {}",l) )
                .switchIfEmpty(LeadExceptions.unreachableLeadException("Lead not found!") );
    }


    @GetMapping("/findLead/{id}/{nome}/{celular}/{telefone}")
    public Flux<LeadRecord> findLead(@PathVariable("id") Long id, @PathVariable("nome") String nome, @PathVariable("celular") String celular, @PathVariable("telefone") String telefone) {
        return findLeadUseCase.findByAnyOfTheseFilters(id,nome,celular,telefone);
    }

    @GetMapping(value = "all")
    public Mono<ResponseEntity<Flux<LeadRecord>>> findAll() {
        var customerResponse = Mono.just(findLeadUseCase.findAll())
                .map(ResponseEntity::ok);
        return customerResponse;
    }

}
