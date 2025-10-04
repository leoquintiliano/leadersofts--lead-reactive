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
public class LeadRepositoryContatoFilter {

//    public Specification<Lead> matchCelular(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.celular())) {
//                String termFilter = '%' + filter.celular().trim() + '%';
//                return cb.like(cb.upper(root.get(Lead_.celular)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCelular2(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.celular2())) {
//                String termFilter = '%' + filter.celular2().trim() + '%';
//                return cb.like(cb.upper(root.get(Lead_.celular2)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchEmail(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.email())) {
//                String termFilter = '%' + filter.email().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.email)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchEndereco(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.endereco())) {
//                String termFilter = '%' + filter.endereco().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.endereco)), termFilter);
//            }
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchUnidadeFederativa(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.uf()))
//                return cb.equal(cb.upper(root.get(Lead_.uf)), filter.uf());
//            return null;
//        };
//    }
//
//    public Specification<Lead> matchCidade(LeadFilter filter) {
//        return (root, query, cb) -> {
//            if (isNotBlank(filter.cidade())) {
//                String termFilter = '%' + filter.cidade().trim().toUpperCase() + '%';
//                return cb.like(cb.upper(root.get(Lead_.cidade)), termFilter);
//            }
//            return null;
//        };
//    }

    public void setContactPredicates(List<Predicate> predicates, CriteriaBuilder cb, Root<Lead> root, LeadFilter filter) {
        if(nonNull(filter.celular()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.celular)), '%' + filter.celular().trim() + '%'));
        if(nonNull(filter.celular2()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.celular2)), '%' + filter.celular2().trim() + '%'));
        if(nonNull(filter.endereco()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.endereco)), '%' + filter.endereco().toUpperCase().trim() + '%'));
        if(nonNull(filter.email()))
            predicates.add(cb.like(cb.upper(root.get(Lead_.email)), '%' + filter.email().toUpperCase().trim() + '%'));
        if(nonNull(filter.uf()))
            predicates.add(cb.equal(cb.upper(root.get(Lead_.uf)), filter.uf().toUpperCase().trim()));
    }


}
