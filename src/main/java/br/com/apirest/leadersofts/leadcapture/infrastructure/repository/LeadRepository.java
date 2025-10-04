package br.com.apirest.leadersofts.leadcapture.infrastructure.repository;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LeadRepository extends ReactiveCrudRepository<Lead, Long> {

//    @Query(value = "SELECT * FROM lead l WHERE l.nome LIKE :#{[0]}")
//    @Query(value = "SELECT * FROM lead l WHERE l.nome LIKE $1")
    @Query(value = "SELECT * FROM lead l WHERE l.nome LIKE CONCAT('%',:nome,'%')")
    Flux<Lead> findByNome(String nome);

    @Query("SELECT * FROM Lead l WHERE l.data_nascimento LIKE CONCAT('%',:bdate,'%')")
    Flux<Lead> findByBirthday(@Param("bdate") String bdate);

    @Query("SELECT l FROM Lead l WHERE l.id = :id")
    Mono<Lead> findById(@Param("id") Long id);

    Mono<Lead> findByCelular(String celular);

    Mono<Lead> findByTelefone(String telefone);

//    @Query("SELECT MAX(l.ID) AS topID FROM Lead l")
    @Query( value = "SELECT MAX(l.id) AS topID FROM Lead l" )
    Flux<Lead> findTopLeadId();

}
