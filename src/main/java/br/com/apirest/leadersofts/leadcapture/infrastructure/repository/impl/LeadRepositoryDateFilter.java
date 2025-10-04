package br.com.apirest.leadersofts.leadcapture.infrastructure.repository.impl;

import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead;
import br.com.apirest.leadersofts.leadcapture.infrastructure.domain.Lead_;
import br.com.apirest.leadersofts.leadcapture.infrastructure.filters.LeadFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Objects.nonNull;

@Component
public class LeadRepositoryDateFilter {

//    public Specification<Lead> matchPrimeiroContato(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.primeiroContato()))
//                return cb.equal(cb.upper(root.get(Lead_.primeiroContato)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchUltimoContato(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.ultimoContato()))
//                return cb.equal(cb.upper(root.get(Lead_.ultimoContato)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchDataNascimento(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.dataNascimento()))
//                return cb.equal(cb.upper(root.get(Lead_.dataNascimento)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchDataVenda0(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.dataNascimento()))
//                return cb.equal(cb.upper(root.get(Lead_.dataVenda)), prepareData(filter.dataVenda()));
//            return null;
//        };
//    }

    public Predicate matchPrimeiroContato0(LeadFilter filter, CriteriaBuilder cb,CriteriaQuery<Lead> query, Root<Lead> root) {
            if (isNotBlank(filter.primeiroContato()))
                return cb.equal(cb.upper(root.get(Lead_.primeiroContato)), prepareData(filter.dataNascimento()));
            return null;
    }

//    public Predicate matchPrimeiroContato(LeadFilter filter, CriteriaBuilder cb1, CriteriaQuery<Lead> query, Root<Lead> root) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.primeiroContato()))
//                return cb.equal(cb.upper(root.get(Lead_.primeiroContato)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }

//    public Predicate matchPrimeiroContato(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.primeiroContato()))
//                return cb.equal(cb.upper(root.get(Lead_.primeiroContato)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }
//
//    public Predicate matchUltimoContato(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.ultimoContato()))
//                return cb.equal(cb.upper(root.get(Lead_.ultimoContato)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }
//
//    public Predicate matchDataNascimento(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.dataNascimento()))
//                return cb.equal(cb.upper(root.get(Lead_.dataNascimento)), prepareData(filter.dataNascimento()));
//            return null;
//        };
//    }

//    public Specification<Lead> matchDataVenda(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.dataNascimento()))
//                return cb.equal(cb.upper(root.get(Lead_.dataVenda)), prepareData(filter.dataVenda()));
//            return null;
//        };
//    }

    public void setDatePredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<?> root, LeadFilter filter ) {
        if(nonNull(filter.primeiroContato()))
            predicates.add(cb.equal(cb.upper(root.get(String.valueOf(Lead_.primeiroContato))), this.prepareData(filter.primeiroContato())));
        if(nonNull(filter.ultimoContato()))
            predicates.add(cb.equal(cb.upper(root.get(String.valueOf(Lead_.ultimoContato))), this.prepareData(filter.ultimoContato())));
        if(nonNull(filter.dataNascimento()))
            predicates.add(cb.equal(cb.upper(root.get(String.valueOf(Lead_.dataNascimento))), this.prepareData(filter.dataNascimento())));
    }

    public String prepareData(String value) {
        if (Objects.nonNull(value) && value.indexOf("/") == 2) {
            return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
                    LocalDate.parse(value.replaceAll("/","-"), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return value;
    }

}
