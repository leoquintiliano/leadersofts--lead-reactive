package br.com.apirest.leadersofts.leadcapture.infrastructure.repository.impl;

import br.com.apirest.leadersofts.leadcapture.infrastructure.config.hibernate.SessionResolver;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import br.com.apirest.leadersofts.leadcapture.infrastructure.repository.LeadRepositoryCustomQuery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class LeadRepositoryCustomQueryImpl implements LeadRepositoryCustomQuery {

    private final SessionResolver sessionResolver;

    //    @PostConstruct
//    public void init() {  var sessionResolver = this.sessionResolver.getSessionFactory(); }

    @Override
    public Flux<Lead> findWithFilter(LeadFilter filter) {
        return this.findLeadWithFilter(filter);
    }

    private StringBuilder getCustomQuery() {
        return new StringBuilder("SELECT L.id, L.nome, L.carro_atual1, L.carro_atual2, L.carro_atual3, L.carro_interesse1,L.carro_interesse2,L.carro_interesse3," +
                "L.celular,L.celular2, L.telefone, L.uf, L.cidade,L.endereco, L.email, L.opcao_veiculo, L.status, L.ultimo_contato, L.vendedor, L.data_nascimento, L.observacoes, " +
                "L.data_cadastro,L.dias_cadastro, L.data_venda, L.dias_venda, L.primeiro_contato, L.dias_ultimo_contato FROM Lead L ");
    }

    private String appendQuery(StringBuilder query, LeadFilter filter) {
        var clause = this.validateNonNullAndGetClause(filter.nome(), filter.celular(),filter.celular2(),filter.telefone(), filter.uf(), filter.cidade(), filter.endereco(),
                filter.carroAtual1(), filter.carroAtual2(), filter.carroAtual3(), filter.carroInteresse1(), filter.carroInteresse2(), filter.carroInteresse3(),
                filter.primeiroContato(), filter.ultimoContato(), filter.dataVenda(),filter.status(), filter.opcaoVeiculo(),filter.email(),filter.observacoes(),
                filter.vendedor());

        if(!clause.isEmpty() && clause.equals(" WHERE ")) {
            query.append(clause);
            this.getContatoQueryParams(query, filter);
            this.preparaFiltrosDadosCarro(query, filter);
        }
        return query.toString();
    }

    private void getContatoQueryParams(StringBuilder query, LeadFilter filter) {
        if(filter.nome() != null && !filter.nome().isEmpty())
            query.append(" UPPER(L.nome) LIKE UPPER('%" + filter.nome() + "%') ");
        if(filter.celular() != null && !filter.celular().isEmpty())
            query.append((this.hasNameClause(query)) ? " AND L.celular LIKE '%" + filter.celular() + "%' OR L.celular2 LIKE '%" + filter.celular() + "%' " : " L.celular LIKE '%" + filter.celular() + "%' OR L.celular2 LIKE '%" + filter.celular() +"%' ");
        if(filter.celular2() != null && !filter.celular2().isEmpty())
            query.append((this.hasNameClause(query)) ? " AND L.celular LIKE '%" + filter.celular2() + "%' OR L.celular2 LIKE '%" + filter.celular2() + "%' " : " L.celular LIKE '%" + filter.celular2() + "%' OR L.celular2 LIKE '%" + filter.celular2() +"%' ");
        if(filter.telefone() != null && !filter.telefone().isEmpty())
            query.append((this.hasNameClause(query)) ? " AND L.telefone LIKE '%" + filter.telefone() + "%' " : " L.telefone LIKE '%"+ filter.telefone() + "%' ");
        if(filter.uf() != null && !filter.uf().isEmpty())
            query.append( this.hasNameClause(query) ? " AND L.uf = " + filter.uf() : " L.uf = " + filter.uf());
        if(filter.cidade() != null && !filter.cidade().isEmpty())
            query.append((this.hasNameClause(query)) ? " AND UPPER(L.cidade) LIKE UPPER('%" + filter.cidade() + "%') " : " UPPER(L.cidade) LIKE UPPER('%"+ filter.cidade() + "%') ");
        if(filter.endereco() != null && !filter.endereco().isEmpty())
            query.append( this.hasNameClause(query) ? " AND UPPER(L.endereco) LIKE UPPER('%" + filter.endereco() + "%') " : " UPPER(L.endereco) LIKE UPPER('%"+ filter.endereco() + "%') ");
        if(filter.email() != null && !filter.email().isEmpty())
            query.append( this.hasNameClause(query) ? " AND UPPER(L.email) LIKE UPPER('%" + filter.email() + "%') " : " UPPER(L.email) LIKE UPPER('%"+ filter.email() + "%') ");
    }

    private void preparaFiltrosDadosCarro(StringBuilder query, LeadFilter filter) {
        getCarroAtualClause(query, filter);
        getCarroInteresseClause(query, filter);
    }

    private void getCarroInteresseClause(StringBuilder query, LeadFilter filter) {
        if(filter.carroInteresse1() != null && !filter.carroInteresse1().isEmpty()) {
            var carroInresseClause = " UPPER(L.carroInteresse1) LIKE UPPER('%" + filter.carroInteresse1() + "%') OR UPPER(L.carroInteresse2) LIKE UPPER('%" + filter.carroInteresse1() + "%') OR UPPER(L.carroInteresse3) LIKE UPPER('%" + filter.carroInteresse1() + "%')";
            query.append( (this.hasNameClause(query)) ? " AND " + carroInresseClause : carroInresseClause );
        }
        if(filter.carroInteresse2() != null && !filter.carroInteresse2().isEmpty()) {
            var carroInresseClause = " UPPER(L.carroInteresse1) LIKE UPPER('%" + filter.carroInteresse2() + "%') OR UPPER(L.carroInteresse2) LIKE UPPER('%" + filter.carroInteresse2() + "%') OR UPPER(L.carroInteresse3) LIKE UPPER('%" + filter.carroInteresse2() + "%')";
            query.append( (this.hasNameClause(query)) ? " AND " + carroInresseClause : carroInresseClause );
        }
        if(filter.carroInteresse3() != null && !filter.carroInteresse3().isEmpty()) {
            var carroInresseClause = " UPPER(L.carroInteresse1) LIKE UPPER('%" + filter.carroInteresse3() + "%') OR UPPER(L.carroInteresse2) LIKE UPPER('%" + filter.carroInteresse3() + "%') OR UPPER(L.carroInteresse3) LIKE UPPER('%" + filter.carroInteresse3() + "%')";
            query.append( (this.hasNameClause(query)) ? " AND " + carroInresseClause : carroInresseClause );
        }
    }

    private void getCarroAtualClause(StringBuilder query, LeadFilter filter) {
        if(filter.carroAtual1() != null && !filter.carroAtual1().isEmpty()) {
            var carroAtual1Clause = " UPPER(L.carroAtual1) LIKE UPPER('%" + filter.carroAtual1() + "%') OR UPPER(L.carroAtual2) LIKE UPPER('%" + filter.carroAtual1() + "%') OR UPPER(L.carroAtual3) LIKE UPPER('%" + filter.carroAtual1() + "%')";
            query.append( (this.hasNameClause(query)) ? " AND " + carroAtual1Clause : carroAtual1Clause );
        }
        if(filter.carroAtual2() != null && !filter.carroAtual2().isEmpty()) {
            var carroAtual2Clause = " UPPER(L.carroAtual1) LIKE UPPER('%" + filter.carroAtual2() + "%') OR UPPER(L.carroAtual2) LIKE UPPER('%" + filter.carroAtual2() + "%') OR UPPER(L.carroAtual3) LIKE UPPER('%" + filter.carroAtual2() + "%')";
            query.append( (this.hasNameClause(query)) ? " AND " + carroAtual2Clause : carroAtual2Clause );
        }
        if(filter.carroAtual3() != null && !filter.carroAtual3().isEmpty()) {
            var carroAtual3Clause = " UPPER(L.carroAtual1) LIKE UPPER('%" + filter.carroAtual3() + "%') OR UPPER(L.carroAtual2) LIKE UPPER('%" + filter.carroAtual3() + "%') OR UPPER(L.carroAtual3) LIKE UPPER('%" + filter.carroAtual3() + "%')";
            query.append( (this.hasNameClause(query)) ? " AND " + carroAtual3Clause : carroAtual3Clause );
        }
    }

    private String validateNonNullAndGetClause(String... field) {
        return Arrays.stream(field)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .stream()
                .filter(Objects::nonNull)
                .filter(e -> !e.isEmpty() && !e.equals(" ") && !e.equals("null"))
                .map(e -> !e.equals("")).count() > 0 ? " WHERE " : "";
    }

    private boolean hasNameClause(StringBuilder query) {
        return (query.toString().contains(" WHERE ") && query.toString().contains("L.nome"));
    }

    public Flux<Lead> findLeadWithFilter(LeadFilter filter) {

        var sqlQuery = appendQuery(getCustomQuery(),filter);

        var result = sessionResolver.getSessionFactory().withStatelessSession( session ->
                session.createNativeQuery(sqlQuery)
                        .getResultList()
                        .invoke(leads -> leads.stream().forEach(System.out::println))
        ).await().indefinitely();

        List<Lead> leads = new ArrayList();

        result.stream().
                forEach( row -> leads.add(getLead((Object[]) row)));

        return Flux.fromIterable(leads);
    }

    private static Lead getLead(Object[] response) {
        return  Lead.builder()
                .id(Objects.nonNull(response[0]) ? Long.valueOf(response[0].toString()) : 1L)
                .nome(Objects.nonNull(response[1].toString()) ? response[1].toString() : "")
                .carroAtual1(Objects.nonNull(response[2]) ? response[2].toString() : "")
                .carroAtual2(Objects.nonNull(response[3]) ? response[3].toString() : "")
                .carroAtual3(Objects.nonNull(response[4]) ? response[4].toString() : "")
                .carroInteresse1(Objects.nonNull(response[5]) ? response[5].toString() : "")
                .carroInteresse2(Objects.nonNull(response[6]) ? response[6].toString() : "")
                .carroInteresse3(Objects.nonNull(response[7]) ? response[7].toString() : "")
                .celular(Objects.nonNull(response[8]) ? response[8].toString() : "")
                .celular2(Objects.nonNull(response[9]) ? response[9].toString() : "")
                .telefone(Objects.nonNull(response[10]) ? response[10].toString() : "")
                .uf(Objects.nonNull(response[11]) ? response[11].toString() : "")
                .cidade(Objects.nonNull(response[12]) ? response[12].toString() : "")
                .endereco(Objects.nonNull(response[13]) ? response[13].toString() : "")
                .email(Objects.nonNull(response[14]) ? response[14].toString() : "")
                .opcaoVeiculo(Objects.nonNull(response[15]) ? response[15].toString() : "")
                .status(Objects.nonNull(response[16]) ? response[16].toString() : "")
                .ultimoContato(Objects.nonNull(response[17]) ? response[17].toString() : "")
                .vendedor(Objects.nonNull(response[18]) ? response[18].toString() : "")
                .dataNascimento(Objects.nonNull(response[19]) ? response[19].toString() : "")
                .observacoes(Objects.nonNull(response[20]) ? response[20].toString() : "")
                .dataCadastro(Objects.nonNull(response[21]) ? response[21].toString() : "")
                .diasCadastro(Objects.nonNull(response[22]) ? Long.valueOf(response[22].toString()) : 0L)
                .dataVenda(Objects.nonNull(response[23]) ? response[23].toString() : "")
                .diasVenda(Objects.nonNull(response[24]) ? Long.valueOf(response[24].toString()) : 0L)
                .primeiroContato(Objects.nonNull(response[25]) ? response[25].toString() : "")
                .diasUltimoContato(Objects.nonNull(response[26]) ? Long.valueOf(response[26].toString()) : 0L)
                .build();
    }

}
