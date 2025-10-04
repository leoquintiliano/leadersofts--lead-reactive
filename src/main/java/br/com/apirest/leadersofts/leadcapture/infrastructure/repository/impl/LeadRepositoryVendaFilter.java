package br.com.apirest.leadersofts.leadcapture.infrastructure.repository.impl;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead_;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Objects.nonNull;

@Component
public class LeadRepositoryVendaFilter {

//    public Specification<Lead> matchCarroInteresse1(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.carroInteresse1())) {
//                String termFilter = '%' + filter.carroInteresse1().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.carroInteresse1)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCarroInteresse2(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.carroInteresse2())) {
//                String termFilter = '%' + filter.carroInteresse2().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.carroInteresse2)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCarroInteresse3(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.carroInteresse3())) {
//                String termFilter = '%' + filter.carroInteresse3().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.carroInteresse3)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCarroAtual1(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.carroAtual1())) {
//                String termFilter = '%' + filter.carroAtual1().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.carroAtual1)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCarroAtual2(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.carroAtual2())) {
//                String termFilter = '%' + filter.carroAtual2().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.carroAtual2)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCarroAtual3(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.carroAtual3())) {
//                String termFilter = '%' + filter.carroAtual3().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.carroAtual3)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchVendedor(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.vendedor())) {
//                String termFilter = '%' + filter.vendedor().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.vendedor)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchStatus(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.status()))
//                return cb.equal(cb.upper(root.get(Lead_.status)), filter.status().trim());
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchOpcaoVeiculo(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.opcaoVeiculo()))
//                return cb.equal(cb.upper(root.get(Lead_.opcaoVeiculo)), filter.opcaoVeiculo().trim());
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchObservacoes(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.observacoes())) {
//                String termFilter = '%' + filter.observacoes().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.observacoes)), termFilter);
//            }
//            return null;
//        };
//    }

    public void setSalePredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Lead> root, LeadFilter filter) {
        if(nonNull(filter.carroAtual1()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.carroAtual1)), filter.carroAtual1().toUpperCase().trim()));
        if(nonNull(filter.carroAtual2()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.carroAtual2)), filter.carroAtual2().toUpperCase().trim()));
        if(nonNull(filter.carroAtual3()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.carroAtual3)), filter.carroAtual3().toUpperCase().trim()));
        if(nonNull(filter.carroInteresse1()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.carroInteresse1)), filter.carroInteresse1().toUpperCase().trim()));
        if(nonNull(filter.carroInteresse2()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.carroInteresse2)), filter.carroInteresse2().toUpperCase().trim()));
        if(nonNull(filter.carroInteresse3()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.carroInteresse3)), filter.carroInteresse3().toUpperCase().trim()));
        if(nonNull(filter.vendedor()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.vendedor)), '%' + filter.vendedor().toUpperCase().trim() + '%'));
        if(nonNull(filter.observacoes()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.observacoes)), '%' + filter.observacoes().toUpperCase().trim() + '%'));
    }


}
